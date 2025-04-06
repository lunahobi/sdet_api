### 📋 Инструкция по запуску тестов
1. Склонируйте себе и разверните [проект](https://github.com/bondarenkokate73/simbirsoft_sdet_project) с сервисом API (инструкция в репозитории)
2. Склонируйте репозиторий
```
git clone https://github.com/lunahobi/sdet_api.git -b dev
```
2. Перейдите в директорию с тестами
```
cd sdet_api
```
3. Запустите тесты
```
mvn clean test allure:serve
```
4. Для параллельного выполнения тестов запустите следующим образом
```
mvn clean test allure:serve -P tests_parallel
```