package libreria

import grails.gorm.transactions.Transactional
import groovy.sql.Sql

@Transactional
class GeneralService {

    def dataSource

    def executeQuery(String sql){
        def db = new Sql(dataSource)

        def rows = db.rows(sql)
        db.close()
        return rows
    }
}
