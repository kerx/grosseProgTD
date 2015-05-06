read -p "Geben Sie bitte den Pfad zu ihren Dateien an. Falls die Dateien sich im selben Ordner befinden, kann die Eingabe leer bleiben: " a
read -p "Geben Sie die Endung der zu nutzenden Dateien an. Falls alle Dateien genutzt werden sollen, geben Sie bitte * an: " b
name=ausgabe$(date "+%s")
datei=$a*.$b
for file in $datei
	do 
	echo $file >> $name
	java -jar GrosseProg10120534.jar $file >> $name
	echo -e "\n" >> $name
done