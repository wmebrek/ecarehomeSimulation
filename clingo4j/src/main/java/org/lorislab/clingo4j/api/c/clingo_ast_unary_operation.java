package org.lorislab.clingo4j.api.c;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
import org.bridj.ann.Name;
/**
 * <i>native declaration : src/main/clingo/lib/c/clingo.h:2099</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("clingo_ast_unary_operation") 
@Library("clingo") 
public class clingo_ast_unary_operation extends StructObject {
	/** C type : clingo_ast_unary_operator_t */
	@Field(0) 
	public int unary_operator() {
		return this.io.getIntField(this, 0);
	}
	/** C type : clingo_ast_unary_operator_t */
	@Field(0) 
	public clingo_ast_unary_operation unary_operator(int unary_operator) {
		this.io.setIntField(this, 0, unary_operator);
		return this;
	}
	/** C type : clingo_ast_term_t */
	@Field(1) 
	public clingo_ast_term argument() {
		return this.io.getNativeObjectField(this, 1);
	}
	/** C type : clingo_ast_term_t */
	@Field(1) 
	public clingo_ast_unary_operation argument(clingo_ast_term argument) {
		this.io.setNativeObjectField(this, 1, argument);
		return this;
	}
	public clingo_ast_unary_operation() {
		super();
	}
	public clingo_ast_unary_operation(Pointer pointer) {
		super(pointer);
	}
}
