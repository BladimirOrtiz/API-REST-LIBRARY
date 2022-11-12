package libreria

import grails.converters.JSON
import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

import java.text.SimpleDateFormat

@ReadOnly
class LibroController {

    LibroService libroService; PaisService paisService; GeneroService generoService

    static responseFormats = ['json', 'xml']
    def paginationLibro(){
        def params = request.getJSON()
        params.max = Math.min(params.max ? params.max as int: 10, 100)
        params.offset = params.offset ? params.offset as int : 0
        def sort = params.sort ?: 'id'
        def order = params.order ?:'asc'

        def libros = libroService.list(sort, order, params)
        def autores = libroService.getAllAutores()
        def generos = generoService.getAll()
        def paises = paisService.getAll()
        render (text: [ list: libros.list, total: libros.total, listaAutores: autores, listaGeneros: generos, listaPaises:paises] as JSON, contentType: 'application/json')
    }
    def getLibro(){
        try {
            def params = request.getJSON()
            if(!params.id){
                throw new Exception("Se requiere un id")
                return
            }
            def libro = libroService.get(params.id as long)
            if(!libro){
                throw  new Exception("No se encuentra el libro solicitado")
            }
            render(text: [data:libro, success: true, message: 'OK' ,style: 'snack-success', title: 'Satisfactorio'] as JSON, contentType: 'application/json')
        }catch(Exception ex){
            render (text: [success: false, message: "Falló al obtener genero. "+ ex.getMessage(), style: "snack-error", title: "Error"] as JSON, contentType: 'application/json')
        }
    }
    def getLibroDetalle(){
        try {
            def params = request.getJSON()
            if(!params.id){
                throw new Exception("Se requiere un id")
                return
            }
            def libro = libroService.getLibroDetalle(libroService.get(params.id as long))
            if(!libro){
                throw  new Exception("No se encuentra el libro solicitado")
            }
            render(text: [data:libro, success: true, message: 'OK' ,style: 'snack-success', title: 'Satisfactorio'] as JSON, contentType: 'application/json')
        }catch(Exception ex){
            render (text: [success: false, message: "Falló al obtener genero. "+ ex.getMessage(), style: "snack-error", title: "Error"] as JSON, contentType: 'application/json')
        }
    }
    def getAllAutores(){
        def listaAutores = libroService.getAllAutores()
        render(text: [data: listaAutores, success: true, message: 'OK.' ,style: 'snack-success', title: 'Satisfactorio'] as JSON, contentType: 'application/json')
    }
    @Transactional
    def save() {
        try {
            def params = request.getJSON()

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
            List generos = params.libro?.generos

            if(params.autor?.id==0 && (!params.autor?.nombreAutor || !params.autor?.fechaNacimiento || params.autor.paisId==0)){
                throw new Exception("Se requieren datos para el autor.")
            }
            if (!params.libro?.titulo || !params.libro?.fechaPublicacion || generos.size()==0) {
                throw new Exception("Se requiere titulo, fecha de publicación o genero.")
                return
            }

            if(params.autor.id>0){
                Autor autorInstance = libroService.getAutor(params.autor?.id as long)
                Libro libroInstance = new Libro()
                libroInstance.titulo = params.libro?.titulo
                libroInstance.fechaPublicacion = params.libro?.fechaPublicacion ? sdf.parse(params.libro?.fechaPublicacion): new Date()
                libroInstance.descripcion = params.libro?.descripcion

                generos.each { genero ->
                    Genero generoInstance = generoService.get(genero.id as long)
                    libroInstance.addToGeneros(generoInstance)
                }
                autorInstance.addToLibros(libroInstance)
                libroService.save(autorInstance)
            }
            else {
                Autor autorInstance = new Autor()
                autorInstance.nombreAutor = params.autor?.nombreAutor
                autorInstance.primerApellido = params.autor?.primerApellido
                autorInstance.segundoApellido = params.autor?.segundoApellido
                autorInstance.fechaNacimiento = params.autor?.fechaNacimiento ? sdf.parse(params.autor?.fechaNacimiento): new Date()
                autorInstance.pais = paisService.get(params.autor?.paisId as long)

                Libro libroInstance = new Libro()
                libroInstance.titulo = params.libro.titulo
                libroInstance.fechaPublicacion = params.libro.fechaPublicacion ? sdf.parse(params.libro.fechaPublicacion): new Date()
                libroInstance.descripcion = params.libro.descripcion

                generos.each { genero ->
                    Genero generoInstance = generoService.get(genero.id as long)
                    libroInstance.addToGeneros(generoInstance)
                }
                autorInstance.addToLibros(libroInstance)
                libroService.save(autorInstance)
            }
            render(text: [success: true, message: 'Libro guardado correctamente.' ,style: 'snack-success', title: 'Satisfactorio'] as JSON, contentType: 'application/json')
        } catch(Exception ex){
            render (text: [success: false, message: "Falló al guardar libro. "+ ex.getMessage(), style: "snack-error", title: "Error"] as JSON, contentType: 'application/json')
        }

    }
    @Transactional
    def update() {
        try {
            def params = request.getJSON()

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
            List generos = params.libro?.generos

            if (!params.libro?.titulo || !params.libro?.fechaPublicacion
                    || generos.size()==0) {
                throw new Exception("Se requiere titulo, fecha de publicación o genero.")
                return
            }

            Libro libroInstance = libroService.get(params?.libro.id as long)
            libroInstance.titulo = params.libro?.titulo
            libroInstance.fechaPublicacion = params.libro?.fechaPublicacion ?
                    sdf.parse(params.libro?.fechaPublicacion): new Date()
            libroInstance.descripcion = params.libro?.descripcion

            libroInstance.generos.clear()

            generos.each { genero ->
                Genero generoInstance = generoService.get(genero.id as long)
                libroInstance.addToGeneros(generoInstance)
            }
            libroService.updateLibro(libroInstance)
            render(text: [success: true, message: 'Libro guardado correctamente.' ,style: 'snack-success', title: 'Satisfactorio'] as JSON, contentType: 'application/json')
        } catch(Exception ex){
            render (text: [success: false, message: "Falló al guardar libro. "+ ex.getMessage(), style: "snack-error", title: "Error"] as JSON, contentType: 'application/json')
        }

    }
    @Transactional
    def delete() {
        try {
            def params = request.getJSON()

            if (!params.id) {
                throw new Exception("Se requiere un libro.")
                return
            }
            Libro libroInstance = libroService.get(params.id as long)
            if(!libroInstance){
                throw new Exception("No se encuentra registrado.")
            }
            libroService.deleteLibro(libroInstance)
            render(text: [success: true, message: 'Libro eliminado correctamente.' ,style: 'snack-success', title: 'Satisfactorio'] as JSON, contentType: 'application/json')
        }catch(Exception ex){
            render (text: [success: false, message: "Falló al eliminar libro. "+ ex.getMessage(), style: "snack-error", title: "Error"] as JSON, contentType: 'application/json')
        }
    }

}
