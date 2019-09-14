package org.lorislab.clingo4j.api.c;
import org.bridj.Callback;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
import org.bridj.ann.Name;
import org.bridj.ann.Ptr;
import org.lorislab.clingo4j.api.c.ClingoLibrary.clingo_propagate_control;
import org.lorislab.clingo4j.api.c.ClingoLibrary.clingo_propagate_init;
/**
 * <i>native declaration : src/main/clingo/lib/c/clingo.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("clingo_propagator") 
@Library("clingo") 
public class clingo_propagator extends StructObject {
	/** C type : init_callback* */
	@Field(0) 
	public Pointer<clingo_propagator.init_callback > init() {
		return this.io.getPointerField(this, 0);
	}
	/** C type : init_callback* */
	@Field(0) 
	public clingo_propagator init(Pointer<clingo_propagator.init_callback > init) {
		this.io.setPointerField(this, 0, init);
		return this;
	}
	/** C type : propagate_callback* */
	@Field(1) 
	public Pointer<clingo_propagator.propagate_callback > propagate() {
		return this.io.getPointerField(this, 1);
	}
	/** C type : propagate_callback* */
	@Field(1) 
	public clingo_propagator propagate(Pointer<clingo_propagator.propagate_callback > propagate) {
		this.io.setPointerField(this, 1, propagate);
		return this;
	}
	/** C type : undo_callback* */
	@Field(2) 
	public Pointer<clingo_propagator.undo_callback > undo() {
		return this.io.getPointerField(this, 2);
	}
	/** C type : undo_callback* */
	@Field(2) 
	public clingo_propagator undo(Pointer<clingo_propagator.undo_callback > undo) {
		this.io.setPointerField(this, 2, undo);
		return this;
	}
	/** C type : check_callback* */
	@Field(3) 
	public Pointer<clingo_propagator.check_callback > check() {
		return this.io.getPointerField(this, 3);
	}
	/** C type : check_callback* */
	@Field(3) 
	public clingo_propagator check(Pointer<clingo_propagator.check_callback > check) {
		this.io.setPointerField(this, 3, check);
		return this;
	}
	/** <i>native declaration : src/main/clingo/lib/c/clingo.h</i> */
	public static abstract class init_callback extends Callback<init_callback > {
		public boolean apply(Pointer<clingo_propagate_init > init, Pointer<? > data) {
			return apply(Pointer.getPeer(init), Pointer.getPeer(data));
		}
		public boolean apply(@Ptr long init, @Ptr long data) {
			return apply((Pointer)Pointer.pointerToAddress(init, clingo_propagate_init.class), Pointer.pointerToAddress(data));
		}
	};
	/** <i>native declaration : src/main/clingo/lib/c/clingo.h</i> */
	public static abstract class propagate_callback extends Callback<propagate_callback > {
		public boolean apply(Pointer<clingo_propagate_control > control, Pointer<Integer > changes, @Ptr long size, Pointer<? > data) {
			return apply(Pointer.getPeer(control), Pointer.getPeer(changes), size, Pointer.getPeer(data));
		}
		public boolean apply(@Ptr long control, @Ptr long changes, @Ptr long size, @Ptr long data) {
			return apply((Pointer)Pointer.pointerToAddress(control, clingo_propagate_control.class), (Pointer)Pointer.pointerToAddress(changes, Integer.class), size, Pointer.pointerToAddress(data));
		}
	};
	/** <i>native declaration : src/main/clingo/lib/c/clingo.h</i> */
	public static abstract class undo_callback extends Callback<undo_callback > {
		public boolean apply(Pointer<clingo_propagate_control > control, Pointer<Integer > changes, @Ptr long size, Pointer<? > data) {
			return apply(Pointer.getPeer(control), Pointer.getPeer(changes), size, Pointer.getPeer(data));
		}
		public boolean apply(@Ptr long control, @Ptr long changes, @Ptr long size, @Ptr long data) {
			return apply((Pointer)Pointer.pointerToAddress(control, clingo_propagate_control.class), (Pointer)Pointer.pointerToAddress(changes, Integer.class), size, Pointer.pointerToAddress(data));
		}
	};
	/** <i>native declaration : src/main/clingo/lib/c/clingo.h</i> */
	public static abstract class check_callback extends Callback<check_callback > {
		public boolean apply(Pointer<clingo_propagate_control > control, Pointer<? > data) {
			return apply(Pointer.getPeer(control), Pointer.getPeer(data));
		}
		public boolean apply(@Ptr long control, @Ptr long data) {
			return apply((Pointer)Pointer.pointerToAddress(control, clingo_propagate_control.class), Pointer.pointerToAddress(data));
		}
	};
	public clingo_propagator() {
		super();
	}
	public clingo_propagator(Pointer pointer) {
		super(pointer);
	}
}
