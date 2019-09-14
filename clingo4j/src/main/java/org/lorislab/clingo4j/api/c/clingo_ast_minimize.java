package org.lorislab.clingo4j.api.c;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
import org.bridj.ann.Name;
import org.bridj.ann.Ptr;
/**
 * <i>native declaration : src/main/clingo/lib/c/clingo.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("clingo_ast_minimize") 
@Library("clingo") 
public class clingo_ast_minimize extends StructObject {
	/** C type : clingo_ast_term_t */
	@Field(0) 
	public clingo_ast_term weight() {
		return this.io.getNativeObjectField(this, 0);
	}
	/** C type : clingo_ast_term_t */
	@Field(0) 
	public clingo_ast_minimize weight(clingo_ast_term weight) {
		this.io.setNativeObjectField(this, 0, weight);
		return this;
	}
	/** C type : clingo_ast_term_t */
	@Field(1) 
	public clingo_ast_term priority() {
		return this.io.getNativeObjectField(this, 1);
	}
	/** C type : clingo_ast_term_t */
	@Field(1) 
	public clingo_ast_minimize priority(clingo_ast_term priority) {
		this.io.setNativeObjectField(this, 1, priority);
		return this;
	}
	/** C type : const clingo_ast_term_t* */
	@Field(2) 
	public Pointer<clingo_ast_term > tuple() {
		return this.io.getPointerField(this, 2);
	}
	/** C type : const clingo_ast_term_t* */
	@Field(2) 
	public clingo_ast_minimize tuple(Pointer<clingo_ast_term > tuple) {
		this.io.setPointerField(this, 2, tuple);
		return this;
	}
	@Ptr 
	@Field(3) 
	public long tuple_size() {
		return this.io.getSizeTField(this, 3);
	}
	@Ptr 
	@Field(3) 
	public clingo_ast_minimize tuple_size(long tuple_size) {
		this.io.setSizeTField(this, 3, tuple_size);
		return this;
	}
	/** C type : const clingo_ast_body_literal_t* */
	@Field(4) 
	public Pointer<clingo_ast_body_literal > body() {
		return this.io.getPointerField(this, 4);
	}
	/** C type : const clingo_ast_body_literal_t* */
	@Field(4) 
	public clingo_ast_minimize body(Pointer<clingo_ast_body_literal > body) {
		this.io.setPointerField(this, 4, body);
		return this;
	}
	@Ptr 
	@Field(5) 
	public long body_size() {
		return this.io.getSizeTField(this, 5);
	}
	@Ptr 
	@Field(5) 
	public clingo_ast_minimize body_size(long body_size) {
		this.io.setSizeTField(this, 5, body_size);
		return this;
	}
	public clingo_ast_minimize() {
		super();
	}
	public clingo_ast_minimize(Pointer pointer) {
		super(pointer);
	}
}
