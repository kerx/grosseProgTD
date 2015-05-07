#!/bin/bash
_suffix="*"
_path="testcases/"
_program="GrosseProg10120534.jar"
function helper {
	echo -e "Usage ./startProgram.sh [options]"
	echo -e "Option\t\t\tDefault\t\tDescription"
	echo -e " -h\t--help\t\t\t\t Show help"
	echo -e " -d\t--default\t\t\t Run script with default path and suffix"
	echo -e " -p\t--path\t\t $_path\t Enter path to test files"
	echo -e " -s\t--suffix\t $_suffix\t\t Enter testfile-suffix"
	echo -e ""
	echo -e "At least you need to deliver path, suffix or default option and the program $_program must be located in the same directory as the script."
	exit
}
function execute {
	name="output$(date "+%s").out"
	if [ "$_suffix" = "*" ]; then
		filename="$_path*"
	else
	filename="$_path*.$_suffix"
	fi
	for file in $filename
		do 
		echo $file >> $name
		java -jar $_program $file >> $name
		echo -e "\n" >> $name
	done
	echo "Your output-file named $name." 
	exit
}
if [ ! -f $_program ]
	 then
	echo -e "$_program isn't in the same directory.\n"
	helper
fi
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
_path=$path
_suffix=$suffix
execute
exit
