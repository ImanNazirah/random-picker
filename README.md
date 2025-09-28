 # 🎲 Random Picker CLI – Spring Boot

This is a simple command-line tool built with **Spring Boot** using `CommandLineRunner`. It randomly selects an item from a list of entries – useful for raffles, giveaways, decision-making, etc.


## 🚀 Features

- Built using **Spring Boot**
- Runs as a **CLI app** via `CommandLineRunner`
- Picks one or more random items from a list
- Easily configurable list (via code, file, or input)

## 🛠️ How It Works

- On application startup, Spring Boot runs any beans that implement `CommandLineRunner`.
- The `DataConfig` class binds a list of selection pool from `application.yml`.
- build the project
```bash 
./mvnw clean install
```
- run the project

```bash 
./run.sh $1 $2
```

![Output](https://raw.githubusercontent.com/ImanNazirah/ImanNazirah/refs/heads/main/docs/output-random-picker.gif)


#### Option for Text to ASCII Art Generator

-https://patorjk.com/software/taag
- figlet