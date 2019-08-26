#!/bin/bash
rsync -aP --delete --exclude={.idea,sync.sh,.git,build,Insur.zip} . root@172.104.41.147:/root/Insur
#rsync -aP --delete --exclude={checkpoints,Insur.zip} ~/Downloads/Insur/nconf/ root@nebula.nubeslab.tech:/root/docs/nconf

#rsync -aP --delete --exclude={.idea,sync.sh,.git,build,Insur.zip} . root@47.254.201.14:/root/Insur
#rsync -aP --delete --exclude={checkpoints,Insur.zip} ~/Downloads/Insur/ root@47.254.201.14:/root/docs

#rsync -aP --delete --exclude={.idea,sync.sh,.git,build,Insur.zip} . root@47.254.197.217:/root/Insur

