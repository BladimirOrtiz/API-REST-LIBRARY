package libreria

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class GeneroServiceSpec extends Specification {

    GeneroService generoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Genero(...).save(flush: true, failOnError: true)
        //new Genero(...).save(flush: true, failOnError: true)
        //Genero genero = new Genero(...).save(flush: true, failOnError: true)
        //new Genero(...).save(flush: true, failOnError: true)
        //new Genero(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //genero.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        generoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Genero> generoList = generoService.list(max: 2, offset: 2)

        then:
        generoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        generoService.count() == 5
    }

    void "test delete"() {
        Long generoId = setupData()

        expect:
        generoService.count() == 5

        when:
        generoService.delete(generoId)
        datastore.currentSession.flush()

        then:
        generoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Genero genero = new Genero()
        generoService.save(genero)

        then:
        genero.id != null
    }
}
