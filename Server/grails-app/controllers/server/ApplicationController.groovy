package server

import grails.converters.JSON
import grails.core.GrailsApplication
import grails.plugins.GrailsPluginManager
import grails.plugins.PluginManagerAware

class ApplicationController implements PluginManagerAware {

    GrailsApplication grailsApplication
    GrailsPluginManager pluginManager
    ParserService parserService

    def index() {
        String path = grailsApplication.config.fileLocation.toString()

        String fl = request.JSON.doc ?: params.doc

        //Load Doc
        String pred = new File(fl).getText('UTF-8')

        render parserService.runNER(pred, path) as JSON
        return
    }
}
