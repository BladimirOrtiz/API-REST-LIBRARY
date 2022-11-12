package libreria

import grails.gorm.services.Service

@Service(Genero)
class GeneroService {

    def save(Genero generoInstance) throws Exception{
        log.info 'Componente: Genero, Service: Genero, Metodo: save, ejecutando.'
        if(generoInstance.validate() && generoInstance.save(flush:true)){
            log.info 'Componente: Genero, Service: Genero, Metodo: save, completado.'
            return generoInstance
        }
        log.error 'Componente: Genero, Service: Genero, Metodo: save, error.'
        throw new Exception()
        return null
    }

    def get(long id){
        return Genero.get(id)
    }
    def getAll(){
        return Genero.getAll()
    }
    def update(Genero generoInstance) throws Exception{
        log.info 'Componente: Genero, Service: Genero, Metodo: update, ejecutando.'
        if(generoInstance.validate() && generoInstance.save(flush:true)){
            log.info 'Componente: Genero, Service: Genero, Metodo: update, completado.'
            return generoInstance
        }
        log.error 'Componente: Genero, Service: Genero, Metodo: update, error.'
        throw new Exception()
        return null
    }
    def delete(Genero generoInstance) throws Exception{
        log.info 'Componente: Genero, Service: Genero, Metodo: delete, ejecutando.'
        if(!generoInstance.delete(flush:true)){
            log.info 'Componente: Genero, Service: Genero, Metodo: delete, completado.'
            return true
        }
        log.error 'Componente: Genero, Service: Genero, Metodo: delete, error.'
        throw new Exception()
        return null
    }
    def list(String sort, String order, params){
        def data = [:]
        String sql = "SELECT NEW MAP(g.id as id, g.nombreGenero as nombreGenero) FROM Genero as g ORDER BY g.${sort} ${order}"
        data.put("list", Pais.executeQuery(sql, [offset: params.offset, max:params.max]))
        data.put("total", Pais.executeQuery("SELECT NEW MAP(count(*) as total) FROM Genero")[0].total)
        return data
    }
}
