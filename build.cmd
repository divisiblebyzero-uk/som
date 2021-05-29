docker build . -t som-compiler
docker run -v %CD%:/home/compiler som-compiler
docker rmi som-compiler --force