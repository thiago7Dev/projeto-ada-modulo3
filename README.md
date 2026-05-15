# Localizador de Arquivos Duplicados (Java)

Este projeto identifica arquivos duplicados em um diretório através da comparação de hashes SHA-256.

## Como Compilar e Usar

### Pré-requisitos
- Java JDK 21 ou superior instalado.

### Passo 1: Compilação
Abra o terminal na pasta raiz do projeto (`projeto-ada-modulo3`) e execute:
```bash
javac -d target/classes src/main/java/org/example/**/*.java src/main/java/org/example/*.java
```

### Passo 2: Preparação de Recursos
Copie o arquivo de configuração para a pasta de classes:
```bash
mkdir -p target/classes
cp src/main/resources/config.properties target/classes/
```

### Passo 3: Execução
Execute o programa apontando para as classes compiladas:
```bash
java -cp target/classes org.example.Main
```

### Dica: Usando Maven (se disponível)
Se você tiver o Maven instalado, pode simplesmente usar:
```bash
mvn compile exec:java -Dexec.mainClass="org.example.Main"
```

## Estrutura do Projeto (Divisão de Tarefas)

### Pessoa A: Scanner e Hashing
- Implementa a varredura de diretórios usando `Files.walk`.
- Calcula o Hash SHA-256 para garantir que a duplicata seja identificada pelo conteúdo, não apenas pelo nome.

### Pessoa B: Processamento e Relatório
- Filtra os resultados para identificar apenas arquivos com hashes repetidos.
- Formata e grava o relatório final de forma persistente usando NIO.2.
