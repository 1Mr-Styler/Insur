#!/bin/bash
rsync -aP --delete --exclude={insuradar.yaml} ~/Downloads/Insur/config/ ./init/config
rsync -aP --delete --exclude={.idea} . styl3r@tog:~/IdeaProjects/INSUR
