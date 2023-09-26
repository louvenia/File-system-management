# File-system-management
Реализация программ для управления файловой системой

## Introduction
- Для написания программ использовалась версия Java 8.
- Отладка кода воспроизводилась на Intellij IDEA CE.
- Правила форматирования кода соответствуют общепринятым стандартам [Oracle](https://www.oracle.com/java/technologies/javase/codeconventions-namingconventions.html).

### Exercise 00

Turn-in directory | ex00
---|---
Files to turn-in |	*.java, signatures.txt
**Permissions**
Recommended types |	Java Collections API (`List<T>`, `Map<K`, `V>`, etc.), InputStream, OutputStream, FileInputStream, FileOutputStream

Реализовано приложение для анализа подписей произвольных файлов. Эта подпись позволяет определить тип содержимого файла и состоит из набора «магических чисел». Эти номера обычно расположены в начале файла. Например, подпись для типа файла PNG представлена первыми восемью байтами файла, которые одинаковы для всех изображений PNG:
```
89 50 4E 47 0D 0A 1A 0A
```
Приложение принимает на вход файл signatures.txt (имя файла явно указано в коде программы). Он содержит список типов файлов и их соответствующие подписи в формате HEX. Наример:
```
PNG, 89 50 4E 47 0D 0A 1A 0A
GIF, 47 49 46 38 37 61
```
Во время выполнения программа принимает полные пути к файлам на жестком диске и сохраняет тип, которому соответствует подпись файла. Результат выполнения программы записывается в файл result.txt. Результат выполнения НЕ ОПРЕДЕЛЕН, если сигнатура этого файла не содержится в signatures.txt.

Пример работы программы:
```
$java Program
-> C:/Users/Admin/images.png
PROCESSED
-> C:/Users/Admin/Games/WoW.iso
PROCESSED
-> 42
```

### Exercise 01

Turn-in directory	| ex01|
---|---
Files to turn-in |	*.java|
**Permissions**
Recommended types |	Java Collections API, Java IO|

Реализовано приложение, которое принимает два файла в качестве входных данных (оба файла передаются в качестве аргументов командной строки) и отображает результат их сравнения сходства (косинусная мера).

Логика работы приложения:
Предположим, в двух файлах содержутся предложения 1 и 2:
```
1. aaa bba bba a ссс
2. bba a a a bb xxx
```
На основе встречающихся слов в файлах создается словарь dictionary.txt:
```
a, aaa, bb, bba, ccc, xxx
```
Затем отражает частоту встречаемости слова относительно словаря:
```
A = (1, 1, 0, 2, 1, 0)
B = (3, 0, 1, 1, 0, 1)
```
Сходство определяется по формуле:

![formula](misc/images/formula.png)

Таким образом, величина сходства для этих векторов равна:
```
Numerator A. B = (1 * 3 + 1 * 0 + 0 * 1 + 2 * 1 + 1 * 0 + 0 * 1) = 5
Denominator ||A|| * ||B|| = sqrt(1 * 1 + 1 * 1 + 0 * 0 + 2  * 2 + 1 * 1 + 0 * 0) * sqrt(3 * 3 + 0 * 0 + 1 * 1 + 1 * 1  + 0 * 0 + 1 * 1) = sqrt(7) * sqrt(12) = 2.64 * 3.46 = 9.1
similarity = 5 / 9.1 = 0.54
```

Пример работы программы:
```
$ java Program inputA.txt inputB.txt
Similarity = 0.54
```

### Exercise 02

Turn-in directory |	ex02
---|---
Files to turn-in |	*.java
**Permissions**
Recommended types	| Java Collections API, Java IO, Files, Paths, etc.

Реализовано приложение, отображающее информацию о файлах, содержимом и размере папок, а также обеспечивающее функцию перемещения/переименования.

В качестве аргумента подается абсолютный путь к папке, откуда начинается работа приложения. Приложение поддерживает следующие команды:

`ls` – отображает текущее содержимое папки;
`ls FOLDER_NAME` - отображает содержимое указанной папки (имена и размеры файлов и подпапок в КБ);
`mv WHAT WHERE` – позволяет перенести или переименовать файл, если `WHERE` содержит имя файла без пути;
`cd FOLDER_NAME` – меняет текущий каталог;
`exit` - выход из приложения.

Предположим, на диске C:/ (или в корневом каталоге, в зависимости от ОС) имеется папка MAIN со следующей иерархией:
- MAIN
  + folder1
    * image.jpg
    *	animation.gif
  +	folder2
    * text.txt
    *	Program.java

Пример работы программы для каталога MAIN:
```
$ java Program --current-folder=C:/MAIN
C:/MAIN
-> ls
folder1 60 KB
folder2 90 KB
-> cd folder1
C:/MAIN/folder1
-> ls
image.jpg 10 KB
animation.gif 50 KB
-> mv image.jpg image2.jpg
-> ls
image2.jpg 10 KB
animation.gif 50 KB
-> mv animation.gif ../folder2
-> ls
image2.jpg 10 KB
-> cd ../folder2
C:/MAIN/folder2
-> ls
text.txt 10 KB
Program.java 80 KB
animation.gif 50 KB
-> exit
```
