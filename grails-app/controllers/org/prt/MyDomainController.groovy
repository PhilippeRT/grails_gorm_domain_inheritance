package org.prt

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class MyDomainController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond MyDomain.list(params), model:[myDomainCount: MyDomain.count()]
    }

    def show(MyDomain myDomain) {
        respond myDomain
    }

    def create() {
        respond new MyDomain(params)
    }

    @Transactional
    def save(MyDomain myDomain) {
        if (myDomain == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (myDomain.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond myDomain.errors, view:'create'
            return
        }

        myDomain.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'myDomain.label', default: 'MyDomain'), myDomain.id])
                redirect myDomain
            }
            '*' { respond myDomain, [status: CREATED] }
        }
    }

    def edit(MyDomain myDomain) {
        respond myDomain
    }

    @Transactional
    def update(MyDomain myDomain) {
        if (myDomain == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (myDomain.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond myDomain.errors, view:'edit'
            return
        }

        myDomain.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'myDomain.label', default: 'MyDomain'), myDomain.id])
                redirect myDomain
            }
            '*'{ respond myDomain, [status: OK] }
        }
    }

    @Transactional
    def delete(MyDomain myDomain) {

        if (myDomain == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        myDomain.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'myDomain.label', default: 'MyDomain'), myDomain.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'myDomain.label', default: 'MyDomain'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
