package insur

class BootStrap {

    def init = { servletContext ->
        environments {
            development {

            }
            production {

            }
        }
    }
    def destroy = {
    }
}
