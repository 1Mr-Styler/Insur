package server

import java.text.SimpleDateFormat

class ParserService {

    HashMap<String, String> runNER(String text, String path) {
        //Run NER.sh
        File pred = new File("${path}pred.txt")
        pred.text = text

        String dirLoc = "/model"
        def sout = new StringBuilder(), serr = new StringBuilder()
        def ner = "$dirLoc/ner.sh ${path}pred.txt".execute()
        ner.consumeProcessOutput(sout, serr)
        ner.waitFor()
        String nerResult = sout
        print("Pred:--> ")
//        println(nerResult)

        nerResult += "\n\t----------NER2--------------\n"

        def location = "$dirLoc/location.sh ${path}pred.txt".execute()
        location.consumeProcessOutput(sout, serr)
        location.waitFor()
        println "out> $sout err> $serr"
        nerResult += sout
        println(nerResult)

        String template = new File("${path}/ner_template.py").getText('UTF-8')

        String g = template.replace("--text--", nerResult)
        File nerpy = new File("${path}ner.py")
        nerpy.text = g

        def reg = "python ${path}ner.py".execute()
        String result = reg.text

        println(result)
        ArrayList<Double> amounts = [0d, 100000d]
        boolean hasAmount = false
        ArrayList<Double> _amounts = new ArrayList<>()
        ArrayList<Date> dates = new ArrayList<>()

        List<String> stuff = result.split(",,,")
        HashMap<String, HashSet<String>> fields = new HashMap<>()
        stuff.eachWithIndex { it, i ->
            if ((i + 1) != stuff.size()) {
                List<String> field = it.split("----")
                println(field)
                field[0] = field[0].replace(",", '').toLowerCase()

                if (field[1] != null) {
                    if (fields.containsKey(field[0])) {
                        if (field[1].contains("\n")) {
                            field[1].split("\n").each { f ->
                                fields[field[0]].add(f)
                            }
                        } else {
                            fields[field[0]].add(field[1])
                        }
                    } else {
                        if (field[1].contains("\n")) {
                            field[1].split("\n").each { f ->
                                fields.put(field[0], new HashSet<>([f]))
                            }
                        } else {
                            fields.put(field[0], new HashSet<>([field[1]]))
                        }
                    }

                    if (field[0] == "amount") {
                        hasAmount = true
                        field[1].split("\n").each { amount ->
                            String stripped = amount.replace("RM", "")
                                    .replace(" ", "")
                                    .replace(",", "")
                                    .replace("MYR", "")
                            Double amt
                            try {
                                amt = Double.parseDouble(stripped)
                                _amounts.add(amt)
                            } catch (NumberFormatException e) {
                                println("Stripped: ${stripped}")
                            }
                        }
                    }

                    if (field[0] == "date") {
                        try {
                            if (field[1].contains("-"))
                                dates.add(new SimpleDateFormat("dd-MM-yyyy").parse(field[1]))
                            else dates.add(new SimpleDateFormat("dd/MM/yyyy").parse(field[1]))
                        } catch (e) {
                            try {
                                dates.add(new SimpleDateFormat("E dd MMM yyyy").parse(field[1]))
                            } catch (ignored) {


                            }
                        }

                    }
                }
            }
        }

//        println(fields)
        if (hasAmount) {
            Collections.sort(_amounts)

            amounts[0] = _amounts.reverse()[0]
            amounts[1] = _amounts.reverse()[1]
            fields.put("coverage", new HashSet<>([amounts[0]]))
            fields.put("premium", new HashSet<>([amounts[1]]))

            fields.remove("amount")
            fields.put("amount", new HashSet<>(_amounts))
        }

        //Date
        Collections.sort(dates);
        if (dates.size() > 1) {
            fields.put("start-date", new HashSet<>([dates[0].format("dd/MM/yyyy")]))
            fields.put("expiry-date", new HashSet<>([dates[-1].format("dd/MM/yyyy")]))
        }

        fields["date"] = null
        fields["date"] = new HashSet<>()
        dates.each {
            date ->
                fields["date"].add(date.format("dd/MM/yyyy"))
        }


        Set locations = new HashSet<>()
        fields["location"].each {
            if (it.matches(".*\\d+.*") || it.length() < 5) {
//                fields["location"].remove(it)
            } else locations.add(it)

        }
        fields["location"] = null
        fields["location"] = locations

        Set issuer = new HashSet<>()
        fields["organization"].each {
            if (it.toLowerCase().contains("berhad") || it.toLowerCase().contains("bhd")) {
                issuer.add(it)
            }
        }

        fields.remove("organization")
        fields["issuer"] = null
        fields["issuer"] = issuer
        fields["person"].each {
            if (!it.contains("\n")) {
                if (fields["name"] == null) {
                    fields.put("name", new HashSet<>([it]))
                } else fields["name"].add(it)
            }
        }

        fields.remove("person")
        println(fields)


        fields
    }

}
