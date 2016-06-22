package br.zul.zwork2.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  TODAS AS CLASSES COM ESSA ANOTAÇÃO TERÃO SUAS INJENÇÕES DE DEPENDÊNCIAS ATUALIZADAS AUTOMÁTICAMENTE
 *  OBS: AINDA É PRECISO QUE SEJA INJECTADO PELO MENOS UMA VEZ PELO MANAGER
 * @author Luiz Henrique
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ZSingleton {}
