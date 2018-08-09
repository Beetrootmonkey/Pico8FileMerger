# Pico8FileMerger
## Usage:
```
java -jar Pico8FileMerger-1.0.0.jar [input folder] [output folder]
```

## Example:
```
java -jar Pico8FileMerger-1.0.0.jar ./src ./carts (will install from 'src' to 'carts')
java -jar Pico8FileMerger-1.0.0.jar ./carts (will install from 'carts' to 'carts')
java -jar Pico8FileMerger-1.0.0.jar (will install from the folder the jar is in to itself)
```

## Project structur:
```
./src/myproject/_main.p8
./src/myproject/lib1.p8
./src/myproject/lib2.p8
```
(those files are .p8 fragments, not real runnable game files)

-> start the tool with 'java -jar Pico8FileMerger-1.0.0.jar ./src ./carts'
-> this will create the following:

```
./carts/myproject.p8
```
(this file now is a runnable pico-8 game)

