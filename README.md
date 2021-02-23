## Prérequis

- IDE pour développement Java
- java > 8 installé sur la machine

## Prise en main APIs Swagger :

- Visiter https://ref.rdd.pc-scol.fr/swagger-ui.html
- Récupération token via curl : `curl -d "username=svc-api&password=???&token=true" -H "Content-Type: application/x-www-form-urlencoded" -X POST https://authn-app.rdd.pc-scol.fr/cas/v1/tickets`
- Appel du endpoint "lecture d'un établissement par code pegase"
- (Optionnel) Appel du endpoint "création d'un nouvel établissement"
- (Optionnel) Exploration des autres APIs

## Prise en main de l'outil de génération de code OpenApiGenerator :

- Visiter https://github.com/OpenAPITools/openapi-generator/blob/master/README.md
- Télécharger : `wget https://repo1.maven.org/maven2/org/openapitools/openapi-generator-cli/5.0.0/openapi-generator-cli-5.0.0.jar -O openapi-generator-cli.jar`
- Créer alias : `alias open-api-generator="java -jar $PWD/openapi-generator-cli.jar"`
- Explorer l'outil : 
    - `open-api-generator --help`
    - `open-api-generator help generate`
    - `open-api-generator list` (https://github.com/OpenAPITools/openapi-generator/blob/v5.0.0/docs/generators)
    - `open-api-generator config-help -g java` (https://github.com/OpenAPITools/openapi-generator/blob/v5.0.0/docs/generators/java.md)
- Générer un client java : `open-api-generator generate -g java -i ./apis/ref-api-1.3.0.yml -c java-gen-config.json -o generated`

## Intégration dans un projet Java

- Visiter https://github.com/OpenAPITools/openapi-generator/blob/v5.0.0/docs/integration.md
- Implémentation avec client java 'native'
- Implémentation avec client java 'feign'


