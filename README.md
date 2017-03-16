# Hamming-encoder

HammingCode.java kodiert Nachrichten mit den Hamming Codes, und kann sie wieder dekodieren, auch wenn höchstens ein 1-Bit-Fehler in jeder 7-Bit-Sequenz vorhanden ist.

Das Programm fragt zuerst den Benutzer, ob er eine Nachricht kodieren oder dekodieren möchte und fragt dann entsprechend Nachricht und Dateiname ab.<br>
Beim Kodieren einer eingegebenen Nachricht wird die Funktion encode aufgerufen, die jeden Charakter der Nachricht in binärer Form umwandelt und entsprechende führende Nullen hinzufügt, wenn nötig. Damit entsteht eine 8-Bit Binärzahl, die in zwei 4-Bit-Sequenzen aufgespaltet wird.<br>
Durch den Aufruf der Funktion parityBits  werden dann zu jeder 4-Bit-Sequenz 3 Parity Bits am Ende hinzugefügt, die gemäß dem Hamming Code berechnet werden.<br>
Schließlich werden alle Gruppen zweier 7-Bit-Sequenzen für jeden Charakter der eingegebenen Nachricht in der angegebenen Datei gespeichert.<br>


Beim Dekodieren einer Nachricht wird die Funktion decode aufgerufen. Diese öffnet die angegebene Datei, scannt jede Linie ein und berechnet die Parity Bits neu. <br>
Wenn nach den Kodieren Fehler in der gespeicherten Datei eingefügt wurden, also wenn die Parity Bits mit den 4 Datenbits nicht übereinstimmen, wird die Funktion correct aufgerufen. <br>
Die Funktion correct berechnet, welche Parity Bits nicht stimmen, und findet damit exakt welches Bit fehlerhaft ist. Sie ruft dann die Funktion invertValue auf, die das angegebene Bit korrigiert, und die korrekte 4-Bit Datensequenz zurückgibt.<br>
Die Funktion decode stellt dann eine 8-Bit-Sequenz aus den zwei kleineren Sequenzen jedes Charakters auf, und ruft dann die Funktion binaryToASCII auf, die diese in den ursprünglichen Charakteren zurückwandelt. <br>

Die ursprüngliche Nachricht wird schließlich auf der Konsole herausgegeben.

