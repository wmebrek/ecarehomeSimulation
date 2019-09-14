package org.lorislab.clingo4j.api.c;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
import org.bridj.ann.Name;
/**
 * <i>native declaration : src/main/clingo/lib/c/clingo.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("clingo_ast_theory_guard") 
@Library("clingo") 
public class clingo_ast_theory_guard extends StructObject {
	/** C type : const char* */
	@Field(0) 
	public Pointer<Byte > operator_name() {
		return this.io.getPointerField(this, 0);
	}
	/** C type : const char* */
	@Field(0) 
	public clingo_ast_theory_guard operator_name(Pointer<Byte > operator_name) {
		this.io.setPointerField(this, 0, operator_name);
		return this;
	}
	/** C type : clingo_ast_theory_term_t */
	@Field(1) 
	public clingo_ast_theory_term term() {
		return this.io.getNativeObjectField(this, 1);
	}
	/** C type : clingo_ast_theory_term_t */
	@Field(1) 
	public clingo_ast_theory_guard term(clingo_ast_theory_term term) {
		this.io.setNativeObjectField(this, 1, term);
		return this;
	}
	public clingo_ast_theory_guard() {
		super();
	}
	public clingo_ast_theory_guard(Pointer pointer) {
		super(pointer);
	}
}
