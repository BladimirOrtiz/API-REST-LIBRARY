package libreria

import grails.converters.JSON
import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class GeneroController {

    GeneroService generoService

    static responseFormats = ['json', 'xml']
    def pagination(){
        def params = request.getJSON()
        params.max = Math.min(params.max ? params.max as int: 10, 100)
        params.offset = params.offset ? params.offset as int : 0
        def sort = params.sort ?: 'id'
        def order = params.order ?:'asc'

        def paises = generoService.list(sort, order, params)
        render (text: [ list: paises.list, total: paises.total] as JSON, contentType: 'application/json')
    }
    def get(){
        try {
            def params = request.getJSON()
            if(!params.id){
                throw new Exception("Se requiere un id")
                return
            }
            def genero = generoService.get(params.id as long)
            if(!genero){
                throw  new Exception("No se encuentra el genero solicitado")
            }
            render(text: [data:genero, success: true, message: 'OK' ,style: 'snack-success', title: 'Satisfactorio'] as JSON, contentType: 'application/json')
        }catch(Exception ex){
            render (text: [success: false, message: "Falló al obtener genero. "+ ex.getMessage(), style: "snack-error", title: "Error"] as JSON, contentType: 'application/json')
        }


    }
    def getAll(){
        def listaGeneros = generoService.getAll()
        render(text: [data: listaGeneros, success: true, message: 'OK.' ,style: 'snack-success', title: 'Satisfactorio'] as JSON, contentType: 'application/json')
    }
    @Transactional
    def save() {
        try {
            def params = request.getJSON()

            if (!params.nombreGenero) {
                throw new Exception("Se requiere un genero.")
                return
            }
            Genero generoInstance = new Genero()
            generoInstance.nombreGenero = params.nombreGenero
            generoService.save(generoInstance)
            render(text: [success: true, message: 'Genero guardado correctamente.' ,style: 'snack-success', title: 'Satisfactorio'] as JSON, contentType: 'application/json')

        } catch(Exception ex){
            render (text: [success: false, message: "Falló al guardar genero. "+ ex.getMessage(), style: "snack-error", title: "Error"] as JSON, contentType: 'application/json')
        }

    }
    @Transactional
    def update(){
        try {
            def params = request.getJSON()

            if (!params.id || !params.nombreGenero) {
                throw new Exception("Se requiere un genero.")
                return
            }
            Genero generoInstance = generoService.get(params.id as long)
            if(!generoInstance){
                throw new Exception("No se encuentra registrado.")
            }
            generoInstance.nombreGenero = params.nombreGenero
            generoService.update(generoInstance)
            render(text: [success: true, message: 'Genero actualizado correctamente.' ,style: 'snack-success', title: 'Satisfactorio'] as JSON, contentType: 'application/json')
        }catch(Exception ex){
            render (text: [success: false, message: "Falló al actualizar genero. "+ ex.getMessage(), style: "snack-error", title: "Error"] as JSON, contentType: 'application/json')
        }
    }
    @Transactional
    def delete() {
        try {
            def params = request.getJSON()

            if (!params.id) {
                throw new Exception("Se requiere un genero.")
                return
            }
            Genero generoInstance = generoService.get(params.id as long)
            if(!generoInstance){
                throw new Exception("No se encuentra registrado.")
            }
            generoService.delete(generoInstance)
            render(text: [success: true, message: 'Genero eliminado correctamente.' ,style: 'snack-success', title: 'Satisfactorio'] as JSON, contentType: 'application/json')
        }catch(Exception ex){
            render (text: [success: false, message: "Falló al eliminar genero. "+ ex.getMessage(), style: "snack-error", title: "Error"] as JSON, contentType: 'application/json')
        }
    }
}
