package libreria

class Libro {

    String titulo
    Date fechaPublicacion
    String descripcion

    static belongsTo = [autor: Autor]
    static hasMany = [generos:Genero]

    static constraints = {
        descripcion nullable:true
    }

}
