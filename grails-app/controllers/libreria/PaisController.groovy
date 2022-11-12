package libreria

import grails.converters.JSON
import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class PaisController {

    PaisService paisService

    static responseFormats = ['json', 'xml']
    def pagination(){
        def params = request.getJSON()
        params.max = Math.min(params.max ? params.max as int: 10, 100)
        params.offset = params.offset ? params.offset as int : 0
        def sort = params.sort ?: 'id'
        def order = params.order ?:'asc'

        def paises = paisService.list(sort, order, params)
        render (text: [ list: paises.list, total: paises.total] as JSON, contentType: 'application/json')
    }
    def get(){
        try {
            def params = request.getJSON()
            if(!params.id){
                throw new Exception("Se requiere un id")
                return
            }
            def pais = paisService.get(params.id as long)
            if(!pais){
                throw  new Exception("No se encuentra el país solicitado")
            }
            render(text: [data:pais, success: true, message: 'OK' ,style: 'snack-success', title: 'Satisfactorio'] as JSON, contentType: 'application/json')
        }catch(Exception ex){
            render (text: [success: false, message: "Falló al obtener país. "+ ex.getMessage(), style: "snack-error", title: "Error"] as JSON, contentType: 'application/json')
        }


    }
    def getAll(){
        def listaPaises = paisService.getAll()
        render(text: [data: listaPaises, success: true, message: 'OK.' ,style: 'snack-success', title: 'Satisfactorio'] as JSON, contentType: 'application/json')
    }
    @Transactional
    def save() {
        try {
            def params = request.getJSON()

            if (!params.nombrePais) {
                throw new Exception("Se requiere un país.")
                return
            }
            Pais paisInstance = new Pais()
            paisInstance.nombrePais = params.nombrePais
            paisService.save(paisInstance)
            render(text: [success: true, message: 'País guardado correctamente.' ,style: 'snack-success', title: 'Satisfactorio'] as JSON, contentType: 'application/json')

        } catch(Exception ex){
            render (text: [success: false, message: "Falló al guardar país. "+ ex.getMessage(), style: "snack-error", title: "Error"] as JSON, contentType: 'application/json')
        }

    }
    @Transactional
    def update(){
        try {
            def params = request.getJSON()

            if (!params.id || !params.nombrePais) {
                throw new Exception("Se requiere un país.")
                return
            }
            Pais paisInstance = paisService.get(params.id as long)
            if(!paisInstance){
                throw new Exception("No se encuentra registrado.")
            }
            paisInstance.nombrePais = params.nombrePais
            paisService.update(paisInstance)
            render(text: [success: true, message: 'País actualizado correctamente.' ,style: 'snack-success', title: 'Satisfactorio'] as JSON, contentType: 'application/json')
        }catch(Exception ex){
            render (text: [success: false, message: "Falló al actualizar país. "+ ex.getMessage(), style: "snack-error", title: "Error"] as JSON, contentType: 'application/json')
        }
    }
    @Transactional
    def delete() {
        try {
            def params = request.getJSON()

            if (!params.id) {
                throw new Exception("Se requiere un país.")
                return
            }
            Pais paisInstance = paisService.get(params.id as long)
            if(!paisInstance){
                throw new Exception("No se encuentra registrado.")
            }
            paisService.delete(paisInstance)
            render(text: [success: true, message: 'País eliminado correctamente.' ,style: 'snack-success', title: 'Satisfactorio'] as JSON, contentType: 'application/json')
        }catch(Exception ex){
            render (text: [success: false, message: "Falló al eliminar país. "+ ex.getMessage(), style: "snack-error", title: "Error"] as JSON, contentType: 'application/json')
        }
    }

}
