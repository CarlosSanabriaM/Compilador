# Compilador (DLP)
En este repositorio están subidas las distintas versiones de un compilador, que ha sido implementado para un lenguaje de programación diseñado en las prácticas de la asignatura "Diseño de Lenguajes de Programación".

- **Las carpetas numeradas** (DLP_PL1, DLP_PL2, ...) representan el estado del compilador, después de implementar lo requerido en cada una de las clases prácticas de la asignatura.
- **La carpeta DLP_AMPL** contiene la penúltima versión del compilador. Es la versión de la última práctica (DLP_PL12), pero con ampliaciones en funcionalidad, como por ejemplo el operador ++ o el operador +=.
- **La carpeta DLP_AMPL (con switch)** contiene la última versión del compilador. Es la versión de la carpeta DLP_PL12, pero añadiendo además la sentencia **break**. *(Leer la Nota al pié)*
- **La carpeta UML** contiene el diagrama UML de clases del compilador. Contiene dos imágenes en formato png y un proyecto Visio (extensión .vsdx), con el cual ha sido realizado dicho diagrama.
- **El archivo pdf** se trata de la documentación del compilador, donde se indica:
    - Los patrones léxicos utilizados en el Análisis Léxico.
    - La gramática libre de contexto utilizada en el Análisis Sintáctico.
    - La gramática abstracta, que representa el AST o Árbol de Sintáxis Abstracta, es decir, el árbol mínimo que preserva la semántica de entrada. Al final, los nodos del árbol se implementan mediante clases de Java.
    - Las gramáticas atribuidas, que se utilizan en el Análisis Semántico.
    - Las plantillas de generación de código, que se utilizan en la Generación de Código.
    - Las ampliaciones realizadas de forma opcional sobre el compilador.

*Nota:* El nombre de dicha carpeta viene debido a que se iba a implementar un bloque switch, pero por falta de tiempo no fue posible. Sin embargo, solo falta implementarlo, puesto que los pasos a seguir para hacerlo se encuentran en la documentación del compilador.
