package insur

import grails.gorm.transactions.Transactional
import sun.misc.BASE64Decoder

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

@Transactional
class DocumentService {

    def b64ToFile(String base64, String file, String ftype) {
        // tokenize the data
        def parts = base64.tokenize(",")
        String imageString = parts[1]

        // create a buffered image
        BufferedImage image
        byte[] imageByte

        BASE64Decoder decoder = new BASE64Decoder();
        imageByte = decoder.decodeBuffer(imageString);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
        image = ImageIO.read(bis);
        bis.close();

        // write the image to a file
        try {
            File outputfile = new File(file)
            ImageIO.write(image, ftype, outputfile)

            return [error: null, status: true]
        } catch (Exception e) {
            log.info(e.getMessage())
            return [error: e.getMessage(), status: false]

        }
    }

    def b64ToFile(def image, String file) {

        // write the image to a file
        try {
            image.transferTo(new File(file))

            return [error: null, status: true]
        } catch (Exception e) {
            log.info(e.getMessage())
            return [error: e.getMessage(), status: false]

        }
    }

    String predict(String file, String path) {
        StringBuilder pred = new StringBuilder()

        try {
            def proc = "sbs ${file} /tmp/out --oem 1".execute()
            proc.waitFor()
            def ypred = new File("/tmp/out.txt").getText('UTF-8').split("\n")

            for (String line : ypred) {
                pred.append(line)
                pred.append("\n")
            }

            return pred.toString()

        } catch (Exception e) {
            throw new Exception(e)
        }
    }

    String prepareFiles(String template, String path, String pred, String pattern) {

        println("pattern ${pattern}")
        println()
        String f = template.replaceFirst("--regex--", pattern)
        String g = f.replace("--text--", pred)
        File regexpy = new File("${path}regex.py")
        regexpy.text = g

        def reg = "python ${path}regex.py".execute()
        String result = reg.text

        return result
    }

    ArrayList<String> populate(String type) {
        switch (type) {
            case "motor":
                ArrayList<String> populate = [
                        "name",
                        "policy_number",
                        "address",
                        "nric",
                        "occupation",
                        "expiry_date",
                        "reg_date",
                        "year",
                        "make",
                        "seater",
                        "coverage_amount",
                        "premium",
                ]
                return populate
                break
            case "health":
                ArrayList<String> populate = [
                        "name",
                        "policy_number",
                        "address",
                        "occupation",
                        "reg_date",
                        "expiry_date",
                        "beneficiary",
                        "coverage_type",
                        "premium",
                ]
                return populate
                break
            case "life":
                ArrayList<String> populate = [
                        "name",
                        "policy_number",
                        "address",
                        "nric",
                        "occupation",
                        "expiry_date",
                        "reg_date",
                        "beneficiary",
                        "mop",
                        "type",
                        "coverage_amount",
                        "premium",
                ]
                return populate
                break
            case "travel":
                ArrayList<String> populate = [
                        "name",
                        "address",
                        "premium",
                        "policy_number",
                        "reg_date",
                        "expiry_date",
                        "occupation",
                        "nric",
                        "coverage_amount",
                ]
                return populate
                break
            default:
                ArrayList<String> populate = [
                        "name",
                        "policy_number",
                        "expiry_date",
                        "coverage_amount",
                        "premium",
                ]
                return populate

        }
    }
}
