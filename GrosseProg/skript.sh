read -p "Geben Sie bitte den Pfad zu ihren Dateien an. Falls die Dateien sich im selben Ordner befinden, kann die Eingabe leer bleiben." pfad
read -p "Geben Sie die Endung der zu nutzenden Dateien an. Falls alle Dateien genutzt werden sollen, geben Sie bitte * an." endung
for file in $pfad*.$endung
	do java -jar GrosseProg.jar $file > "ausgabe"+ date +"%T"
done