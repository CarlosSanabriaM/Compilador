java -cp tools/jflex/JFlex.jar JFlex.Main -d src/scanner src/scanner/scanner.jflex
cd tools
cd byaccj
./yacc.macosx -J -v -Jpackage=parser -Jsemantic=Object "../../src/parser/parser.y"
mv Parser.java ../../src/parser
mv y.output ../../src/parser