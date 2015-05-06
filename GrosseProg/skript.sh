read -p "Geben Sie bitte den Pfad zu ihren Dateien an. Falls die Dateien sich im selben Ordner befinden, kann die Eingabe leer bleiben: " a
read -p "Geben Sie die Endung der zu nutzenden Dateien an. Falls alle Dateien genutzt werden sollen, geben Sie bitte * an: " b
datum=date+%s
datei=$a*.$b
echo $datei
for file in $datei
	do java -jar GrosseProg10120534.jar $file > "ausgabe"+$datum
done