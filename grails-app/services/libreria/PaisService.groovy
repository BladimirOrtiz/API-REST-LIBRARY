package libreria

import grails.gorm.services.Service

@Service(Pais)
class PaisService {

    def save(Pais paisInstance) throws Exception{
        log.info 'Componente: Pais, Service: Pais, Metodo: save, ejecutando.'
        if(paisInstance.validate() && paisInstance.save(flush:true)){
            log.info 'Componente: Pais, Service: Pais, Metodo: save, completado.'
            return paisInstance
        }
        log.error 'Componente: Pais, Service: Pais, Metodo: save, error.'
        throw new Exception()
        return null
    }

    def get(long id){
        return Pais.get(id)
    }
    def getAll(){
        return Pais.getAll()
    }
    def update(Pais paisInstance) throws Exception{
        log.info 'Componente: Pais, Service: Pais, Metodo: update, ejecutando.'
        if(paisInstance.validate() && paisInstance.save(flush:true)){
            log.info 'Componente: Pais, Service: Pais, Metodo: update, completado.'
            return paisInstance
        }
        log.error 'Componente: Pais, Service: Pais, Metodo: update, error.'
        throw new Exception()
        return null
    }
    def delete(Pais paisInstance) throws Exception{
        log.info 'Componente: Pais, Service: Pais, Metodo: delete, ejecutando.'
        if(!paisInstance.delete(flush:true)){
            log.info 'Componente: Pais, Service: Pais, Metodo: delete, completado.'
            return true
        }
        log.error 'Componente: Pais, Service: Pais, Metodo: delete, error.'
        throw new Exception()
        return null
    }
    def list(String sort, String order, params){
        def data = [:]
        String sql = "SELECT NEW MAP(p.id as id, p.nombrePais as nombrePais) FROM Pais as p ORDER BY p.${sort} ${order}"
        data.put("list", Pais.executeQuery(sql, [offset: params.offset, max:params.max]))
        data.put("total", Pais.executeQuery("SELECT NEW MAP(count(*) as total) FROM Pais")[0].total)
        return data

    }

}
