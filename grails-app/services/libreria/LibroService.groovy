package libreria

import grails.gorm.services.Service
import org.apache.commons.io.FilenameUtils
import org.springframework.web.multipart.MultipartFile

import java.text.SimpleDateFormat

@Service(Libro)
class LibroService {
    def get(long id){
        return Libro.get(id)
    }
    def getAutor(long id){
        return Autor.get(id)
    }
    def getAllAutores(){
        return Autor.getAll()
    }
    def getLibroDetalle(Libro libroInstance){
        List generosList = []
        long idAutor = libroInstance.autor.id
        def autor = [id: libroInstance.autor.id, nombreAutor: libroInstance.autor.nombreAutor,
                     primerApellido: libroInstance.autor.primerApellido, segundoApellido:libroInstance.autor.segundoApellido,
                fechaNacimiento: libroInstance.autor.fechaNacimiento, paisId: libroInstance.autor.paisId
        ]
        libroInstance?.generos?.each { genero ->
            generosList<<[id: genero?.id, nombreGenero: genero?.nombreGenero]
        }
        def libro = [id: libroInstance.id, titulo: libroInstance.titulo,fechaPublicacion:libroInstance.fechaPublicacion, descripcion:libroInstance.descripcion, generos:generosList]
        return [autor: autor, libro:libro]
    }
    def save(Autor autorInstance) throws Exception{
        log.info 'Componente: Libro, Service: Libro, Metodo: save, ejecutando.'
        if(autorInstance.validate() && autorInstance.save(flush:true)){
            log.info 'Componente: Libro, Service: Libro, Metodo: save, completado.'
            return autorInstance
        }
        log.error 'Componente: Libro, Service: Libro, Metodo: save, error.'
        throw new Exception().getMessage() + autorInstance.hasErrors()
        return null
    }
    def updateLibro(Libro libroInstance) throws Exception{
        log.info 'Componente: Libro, Service: Libro, Metodo: update, ejecutando.'
        if(libroInstance.validate() && libroInstance.save(flush:true)){
            log.info 'Componente: Libro, Service: Libro, Metodo: update, completado.'
            return libroInstance
        }
        log.error 'Componente: Libro, Service: Libro, Metodo: update, error.'
        throw new Exception()
        return null
    }
    def list(String sort, String order, params){
        def data = [:]
        String sql = "SELECT NEW MAP( lb.id as id, lb.titulo as titulo, a.nombreAutor as nombreAutor) FROM Libro as lb  left join Autor as a on lb.autor.id=a.id ORDER BY ${sort} ${order}"
        data.put("list", Pais.executeQuery(sql, [offset: params.offset, max:params.max]))
        data.put("total", Pais.executeQuery("SELECT NEW MAP(count(*) as total) FROM Libro")[0].total)
        return data
    }
    def deleteLibro(Libro libroInstance) throws Exception{
        log.info 'Componente: Libro, Service: Libro, Metodo: delete, ejecutando.'
        if(!libroInstance.delete(flush:true)){
            log.info 'Componente: Libro, Service: Libro, Metodo: delete, completado.'
            return true
        }
        log.error 'Componente: Libro, Service: Libro, Metodo: delete, error.'
        throw new Exception()
        return null
    }

}
