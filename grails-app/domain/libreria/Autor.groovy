package libreria

class Autor {

    String nombreAutor
    String primerApellido
    String segundoApellido
    Date fechaNacimiento
    Pais pais

    static hasMany = [libros:Libro]
    static constraints = {
        primerApellido nullable: true
        segundoApellido nullable: true
    }
}
