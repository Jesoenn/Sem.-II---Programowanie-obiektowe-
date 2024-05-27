Do zrobienia:
1. Kwadrat na srodku | MAM
2. "silnik" - 60 fps | MAM
3. Samochod wyswietla sie na mapie (x,y,wys,szer,predkosc) | MAM
4. Samochod porusza sie w jakis punkt | MAM
5. Samochod to zdjecie | MAM
6. Samochod obraca sie w zaleznosci od kierunku | MAM -> (gora-lewo->up; gora-lewo->up; dol-lewo->down; dol-prawo->down; gora->up; dol->down; prawo->right; lewo->left)
7. Dodaje 2gi samochod | MAM
8. Samochody maja id i jada w swoja strone | MAM
9. Dodanie tla do mapy | MAM
10. Podzielenie mapy na kwadraty | MAM
11. tablica dwuwymiarowa z zapelnieniem mapy - defaultowo 0 | MAM
12. Zapelnienie mapy samochodamizwyklymi | MAM
13. Tworzenie samochodow na podstawie zmiennej map w Mapa - ArrayList | MAM!!!!!
14. Poruszanie sie samochodow w swoja strone - losowe wybranie | MAM
15. Stworzenie klas sciana | MAM
16. Losowe wygenerowanie scian (długość - 2 kwadraty) w losowym kierunku | MAM z malym bugiem ale sie napraw
    16a) bug fixed, dadana rotacja ścian
18. Wjazd w sciane = stop - bez omijania na razie | MAM
19. nitro | JEST
21. klasy pochodne od klasy Samochod | JEST (narazie bez rozpisanych metod bo trzeba dokonczyc inne na np. utrate zycia)
22. Wyświetlanie nitro na mapie | MAM
23. Duze zmiany w movemencie; Obecnie zderzenie z samochodem i sciana =speed 0 | MAM
24. 
25. po zderzeniu ze sciana zmiana celu na losowe pole na mapie (nie sciana) | MAM
26. jak dojedzie do pola to szuka nowego celu samochodu | MAM
27. zderzenie z samochodem=nowy cel auto | MAM
28. FOTA NITRO DO ZMIANY !!!!!!!
29. dodac hp
30. zderzenie z fura -HP dla fury z ktora sie zderzyl, dla samochodu zderzajacego 3 razy mniej obrazen
31. 0HP=stoi w miejscu
32. dodanie zeby jak fura ma 0HP to nie byla brana pod uwage w szukaniu targetu
33. interakcja nitra z samochodem

Sciana pionowa = 1 na mapie
Sciana pozioma = 2 na mapie
Nitro=6 na mapie
SamochodZwykly=3 na mapie
SamochodWybuchowy=4 na mapie
Oponowy=5  na mapie

liczba uderzen samochodow, 
Na potem: Wszystkie samochody to bedzie 1 arrayList w derbach jezeli da rade - id beda szly po kolei od 0
