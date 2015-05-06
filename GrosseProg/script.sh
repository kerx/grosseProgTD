#!/bin/bash
suffix="*"
path=""
function helper {
	echo -e "Usage ./script.sh [options]"
	echo -e "Option\t\t\tDefault\t\tDescription"
	echo -e " -h\t--help\t\t\t\t Show help"
	echo -e " -d\t--default\t\t\t Run script with default path and suffix"
	echo -e " -p\t--path\t\t .\t\t Enter path to test files"
	echo -e " -s\t--suffix\t *\t\t Enter testfile-suffix"
	echo -e ""
	echo -e "At least you need to deliver path, suffix or default option."
	exit
}
function execute {
	name="output$(date "+%s").out"
	if [ "$suffix" = "*" ]; then
		filename="$path*"
	else
	filename="$path*.$suffix"
	fi
	for file in $filename
		do 
		echo $file >> $name
		java -jar GrosseProg10120534.jar $file >> $name
		echo -e "\n" >> $name
	done
	echo "Your output-file named $name." 
	exit
}

if [ "$#" = "0" ]; then
	helper
else 	
	option=""
	for var in "$@"
	do
		if [ "$option" = "p" ]; then
			path=$var
			option=""
		fi
		if [ "$option" = "s" ]; then
			suffix="$var"
			option=""
		fi
		if [ "$var" = "-p" ] || [ "$var" = "--path" ];then
			option="p"
		fi
		if [ "$var" = "-s" ] || [ "$var" = "--suffix" ];then
			option="s"
		fi
		if [ "$var" = "-h" ] || [ "$var" = "-help" ]; then
			helper
		fi
		if [ "$var" = "-d" ] || [ "$var" = "-default" ]; then
			execute
		fi
	done
	if [ "$option" != "" ]; then
		echo -e "Wrong parameter usage"
		helper
	fi
fi
execute
exit
