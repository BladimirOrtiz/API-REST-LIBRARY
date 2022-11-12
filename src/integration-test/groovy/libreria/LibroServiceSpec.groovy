package libreria

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class LibroServiceSpec extends Specification {

    LibroService libroService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Libro(...).save(flush: true, failOnError: true)
        //new Libro(...).save(flush: true, failOnError: true)
        //Libro libro = new Libro(...).save(flush: true, failOnError: true)
        //new Libro(...).save(flush: true, failOnError: true)
        //new Libro(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //libro.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        libroService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Libro> libroList = libroService.list(max: 2, offset: 2)

        then:
        libroList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        libroService.count() == 5
    }

    void "test delete"() {
        Long libroId = setupData()

        expect:
        libroService.count() == 5

        when:
        libroService.delete(libroId)
        datastore.currentSession.flush()

        then:
        libroService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Libro libro = new Libro()
        libroService.save(libro)

        then:
        libro.id != null
    }
}
