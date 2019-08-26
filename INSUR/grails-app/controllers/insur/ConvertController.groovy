package insur

import java.time.LocalDate

class ConvertController {
    static responseFormats = ['json', 'xml']
    def documentService

    def save() {
        String req = request.getHeader("Content-Type")
        def document
        String type
        String file
        String path = grailsApplication.config.fileLocation.toString()

        if (req.contains('json')) {
            String b64 = request.JSON.base64[0] ?: request.JSON.base64
            type = b64.toString().find("(jpeg|png|jpg)")
            file = "/tmp/${LocalDate.now()}.${type}"

            document = documentService.b64ToFile(b64, file, type)

            println(document)

        } else {
            document = request.getFile('doc')
            type = document.originalFilename?.toLowerCase()?.find("(jpeg|png|jpg)")
            file = "/tmp/${LocalDate.now()}.${type}"

            document.transferTo new File(file)

        }

        if (type == null || type.empty) {
            render('Unsupported document format')
            return
        }

        String ypred = documentService.predict(file, path)

        render(ypred)
    }
}
