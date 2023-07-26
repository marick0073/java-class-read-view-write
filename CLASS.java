import java.io.File;
import java.io.RandomAccessFile;

public class CLASS{

	class cp_info{

		byte tag;

		abstract class CONSTANT{

			abstract CONSTANT read(RandomAccessFile raf) throws Exception;
			abstract CONSTANT write(RandomAccessFile raf) throws Exception;

		}

		class CONSTANT_Constant extends CONSTANT{

			byte bytes[];

			CONSTANT_Constant read(RandomAccessFile raf) throws Exception{

				switch(tag){

					case  1: bytes=new byte[2+raf.readUnsignedShort()];
							 raf.seek(raf.getFilePointer()-2); break;
					case  3: bytes=new byte[4]; break;
					case  4: bytes=new byte[4]; break;
					case  5: bytes=new byte[8]; break;
					case  6: bytes=new byte[8]; break;
					case  7: bytes=new byte[2]; break;
					case  8: bytes=new byte[2]; break;
					case  9: bytes=new byte[4]; break;
					case 10: bytes=new byte[4]; break;
					case 11: bytes=new byte[4]; break;
					case 12: bytes=new byte[4]; break;
					case 15: bytes=new byte[3]; break;
					case 16: bytes=new byte[2]; break;
					case 18: bytes=new byte[4]; break;

					default: System.exit(3);

				}

				raf.readFully(bytes);

				return this;

			}

			CONSTANT_Constant write(RandomAccessFile raf) throws Exception{

				raf.write(bytes);

				return this;

			}

		}

		class CONSTANT_Utf8 extends CONSTANT{

			byte bytes[];
			byte orig_bytes[];

			CONSTANT_Utf8 read(RandomAccessFile raf) throws Exception{

				bytes=orig_bytes=new byte[raf.readUnsignedShort()];
				raf.readFully(bytes);

				return this;

			}

			CONSTANT_Utf8 write(RandomAccessFile raf) throws Exception{

				raf.writeShort(bytes.length);
				raf.write(bytes);

				return this;

			}

			String String(){

				return new String(bytes);

			}

		}

		class CONSTANT_Class extends CONSTANT{

			short name_index;

			CONSTANT_Class read(RandomAccessFile raf) throws Exception{

				name_index=raf.readShort();

				return this;

			}

			CONSTANT_Class write(RandomAccessFile raf) throws Exception{

				raf.writeShort(name_index);

				return this;

			}

			CONSTANT_Utf8 _name(){

				return (CONSTANT_Utf8)constant_pool[UTILS.toUnsignedInt(name_index)-1].info;

			}

		}

		class CONSTANT_ref extends CONSTANT{

			short class_index;
			short name_and_type_index;

			CONSTANT_ref read(RandomAccessFile raf) throws Exception{

				class_index=raf.readShort();
				name_and_type_index=raf.readShort();

				return this;

			}

			CONSTANT_ref write(RandomAccessFile raf) throws Exception{

				raf.writeShort(class_index);
				raf.writeShort(name_and_type_index);

				return this;

			}

			CONSTANT_Class _class(){

				return (CONSTANT_Class)constant_pool[UTILS.toUnsignedInt(class_index)-1].info;

			}

			CONSTANT_NameAndType _name_and_type(){

				return (CONSTANT_NameAndType)constant_pool[UTILS.toUnsignedInt(name_and_type_index)-1].info;

			}

		}

		class CONSTANT_NameAndType extends CONSTANT{

			short name_index;
			short descriptor_index;

			CONSTANT_NameAndType read(RandomAccessFile raf) throws Exception{

				name_index=raf.readShort();
				descriptor_index=raf.readShort();

				return this;

			}

			CONSTANT_NameAndType write(RandomAccessFile raf) throws Exception{

				raf.writeShort(name_index);
				raf.writeShort(descriptor_index);

				return this;

			}

			CONSTANT_Utf8 _name(){

				return (CONSTANT_Utf8)constant_pool[UTILS.toUnsignedInt(name_index)-1].info;

			}

		}

		CONSTANT info;

		cp_info read(RandomAccessFile raf) throws Exception{

			tag=raf.readByte();

			switch(tag){

				case  1: info=new CONSTANT_Utf8(); break;
				case  3: info=new CONSTANT_Constant(); break;
				case  4: info=new CONSTANT_Constant(); break;
				case  5: info=new CONSTANT_Constant(); break;
				case  6: info=new CONSTANT_Constant(); break;
				case  7: info=new CONSTANT_Class(); break;
				case  8: info=new CONSTANT_Constant(); break;
				case  9: info=new CONSTANT_ref(); break;
				case 10: info=new CONSTANT_ref(); break;
				case 11: info=new CONSTANT_ref(); break;
				case 12: info=new CONSTANT_NameAndType(); break;
				case 15: info=new CONSTANT_Constant(); break;
				case 16: info=new CONSTANT_Constant(); break;
				case 18: info=new CONSTANT_Constant(); break;

				default: System.exit(3);

			}

			info.read(raf);

			return this;

		}

		cp_info write(RandomAccessFile raf) throws Exception{

			raf.writeByte(tag);
			info.write(raf);

			return this;

		}

		CONSTANT_Utf8 Utf8(){

			return (CONSTANT_Utf8)info;

		}

	}

	class member_info{

		short access_flags;
		short name_index;
		short descriptor_index;
		attribute_info attributes[];

		member_info read(RandomAccessFile raf) throws Exception{

			access_flags=raf.readShort();
			name_index=raf.readShort();
			descriptor_index=raf.readShort();

			attributes=new attribute_info[raf.readUnsignedShort()];
			for(int i=0;i<attributes.length;i++)
				attributes[i]=new attribute_info().read(raf);

			return this;

		}

		member_info write(RandomAccessFile raf) throws Exception{

			raf.writeShort(access_flags);
			raf.writeShort(name_index);
			raf.writeShort(descriptor_index);

			raf.writeShort(attributes.length);
			for(int i=0;i<attributes.length;i++)
				attributes[i].write(raf);

			return this;

		}

		cp_info.CONSTANT_Utf8 _name(){

			return (cp_info.CONSTANT_Utf8)constant_pool[UTILS.toUnsignedInt(name_index)-1].info;

		}

	}

	class attribute_info{

		short attribute_name_index;
    		int attribute_length;
		attribute info;

		abstract class attribute{

			abstract attribute read(RandomAccessFile raf) throws Exception;
			abstract attribute write(RandomAccessFile raf) throws Exception;

		}

		class Attribute_attribute extends attribute{

			byte info[];

			Attribute_attribute read(RandomAccessFile raf) throws Exception{

				info=new byte[attribute_length];
				raf.readFully(info);

				return this;

			}

			Attribute_attribute write(RandomAccessFile raf) throws Exception{

				raf.write(info);

				return this;

			}

		}

		class InnerClasses_attribute extends attribute{

			class class_structure{

				short inner_class_info_index;
      			short outer_class_info_index;
				short inner_name_index;
				short inner_class_access_flags;

				class_structure read(RandomAccessFile raf) throws Exception{

					inner_class_info_index=raf.readShort();
					outer_class_info_index=raf.readShort();
					inner_name_index=raf.readShort();
					inner_class_access_flags=raf.readShort();

					return this;

				}

				class_structure write(RandomAccessFile raf) throws Exception{

					raf.writeShort(inner_class_info_index);
					raf.writeShort(outer_class_info_index);
					raf.writeShort(inner_name_index);
					raf.writeShort(inner_class_access_flags);

					return this;

				}

				cp_info.CONSTANT_Class _inner_class_info(){

					return (cp_info.CONSTANT_Class)constant_pool[UTILS.toUnsignedInt(inner_class_info_index)-1].info;

				}

				cp_info.CONSTANT_Utf8 _inner_name(){

					return (cp_info.CONSTANT_Utf8)constant_pool[UTILS.toUnsignedInt(inner_name_index)-1].info;

				}

			}

			class_structure classes[];

			InnerClasses_attribute read(RandomAccessFile raf) throws Exception{

				classes=new class_structure[raf.readUnsignedShort()];
				for(int i=0;i<classes.length;i++)
					classes[i]=new class_structure().read(raf);

				return this;

			}

			InnerClasses_attribute write(RandomAccessFile raf) throws Exception{

				raf.writeShort(classes.length);
				for(int i=0;i<classes.length;i++)
					classes[i].write(raf);

				return this;

			}

		}

		class SourceFile_attribute extends attribute{

			short sourcefile_index;

			SourceFile_attribute read(RandomAccessFile raf) throws Exception{

				sourcefile_index=raf.readShort();

				return this;

			}

			SourceFile_attribute write(RandomAccessFile raf) throws Exception{

				raf.writeShort(sourcefile_index);

				return this;

			}

			cp_info.CONSTANT_Utf8 _sourcefile(){

				return (cp_info.CONSTANT_Utf8)constant_pool[UTILS.toUnsignedInt(sourcefile_index)-1].info;

			}

		}

		attribute_info read(RandomAccessFile raf) throws Exception{

			attribute_name_index=raf.readShort();
			attribute_length=raf.readInt();

			switch(new String(_attribute_name().bytes)){

				case "ConstantValue": info=new Attribute_attribute(); break;
				case "Code": info=new Attribute_attribute(); break;
				case "StackMapTable": info=new Attribute_attribute(); break;
				case "Exceptions": info=new Attribute_attribute(); break;
				case "InnerClasses": info=new InnerClasses_attribute(); break;
				case "EnclosingMethod": info=new Attribute_attribute(); break;
				case "Synthetic": info=new Attribute_attribute(); break;
				case "Signature": info=new Attribute_attribute(); break;
				case "SourceFile": info=new SourceFile_attribute(); break;
				case "SourceDebugExtension": info=new Attribute_attribute(); break;
				case "LineNumberTable": info=new Attribute_attribute(); break;
				case "LocalVariableTable": info=new Attribute_attribute(); break;
				case "LocalVariableTypeTable": info=new Attribute_attribute(); break;
				case "Deprecated": info=new Attribute_attribute(); break;
				case "RuntimeVisibleAnnotations": info=new Attribute_attribute(); break;
				case "RuntimeInvisibleAnnotations": info=new Attribute_attribute(); break;
				case "RuntimeVisibleParameterAnnotations": info=new Attribute_attribute(); break; 
				case "RuntimeInvisibleParameterAnnotations": info=new Attribute_attribute(); break;
				case "RuntimeVisibleTypeAnnotations": info=new Attribute_attribute(); break;
				case "RuntimeInvisibleTypeAnnotations": info=new Attribute_attribute(); break;
				case "AnnotationDefault": info=new Attribute_attribute(); break; 
				case "BootstrapMethods": info=new Attribute_attribute(); break;
				case "MethodParameters": info=new Attribute_attribute(); break;

				default: System.exit(4);

			}

			info.read(raf);

			return this;

		}

		attribute_info write(RandomAccessFile raf) throws Exception{

			raf.writeShort(attribute_name_index);
			raf.writeInt(attribute_length);
			info.write(raf);

			return this;

		}

		cp_info.CONSTANT_Utf8 _attribute_name(){

			return (cp_info.CONSTANT_Utf8)constant_pool[UTILS.toUnsignedInt(attribute_name_index)-1].info;

		}

		InnerClasses_attribute InnerClasses(){

			return (InnerClasses_attribute)info;

		}

		SourceFile_attribute SourceFile(){

			return (SourceFile_attribute)info;

		}

	}

	int magic=0;
	short minor_version=0;
	short major_version=0;
	cp_info constant_pool[]=new cp_info[0];
	short access_flags=0;
	short this_class=0;
	short super_class=0;
	short interfaces[]=new short[0];
	member_info fields[]=new member_info[0];
	member_info methods[]=new member_info[0];
	attribute_info attributes[]=new attribute_info[0];

	CLASS read(RandomAccessFile raf) throws Exception{

		magic=raf.readInt();
		minor_version=raf.readShort();
		major_version=raf.readShort();

		constant_pool=new cp_info[raf.readUnsignedShort()-1];
		for(int i=0;i<constant_pool.length;i++)
			constant_pool[i]=new cp_info().read(raf);

		access_flags=raf.readShort();
		this_class=raf.readShort();
		super_class=raf.readShort();

		interfaces=new short[raf.readUnsignedShort()];
		for(int i=0;i<interfaces.length;i++)
			interfaces[i]=raf.readShort();

		fields=new member_info[raf.readUnsignedShort()];
		for(int i=0;i<fields.length;i++)
			fields[i]=new member_info().read(raf);

		methods=new member_info[raf.readUnsignedShort()];
		for(int i=0;i<methods.length;i++)
			methods[i]=new member_info().read(raf);

		attributes=new attribute_info[raf.readUnsignedShort()];
		for(int i=0;i<attributes.length;i++)
			attributes[i]=new attribute_info().read(raf);

		return this;

	}

	CLASS write(RandomAccessFile raf) throws Exception{

		raf.writeInt(magic);
		raf.writeShort(minor_version);
		raf.writeShort(major_version);

		raf.writeShort(constant_pool.length+1);
		for(int i=0;i<constant_pool.length;i++)
			constant_pool[i].write(raf);

		raf.writeShort(access_flags);
		raf.writeShort(this_class);
		raf.writeShort(super_class);

		raf.writeShort(interfaces.length);
		for(int i=0;i<interfaces.length;i++)
			raf.writeShort(interfaces[i]);

		raf.writeShort(fields.length);
		for(int i=0;i<fields.length;i++)
			fields[i].write(raf);

		raf.writeShort(methods.length);
		for(int i=0;i<methods.length;i++)
			methods[i].write(raf);

		raf.writeShort(attributes.length);
		for(int i=0;i<attributes.length;i++)
			attributes[i].write(raf);

		return this;

	}

	cp_info.CONSTANT_Class _this_class(){

		return (cp_info.CONSTANT_Class)constant_pool[UTILS.toUnsignedInt(this_class)-1].info;

	}

	public static void main(String[] args) throws Exception{

		CLASS c=new CLASS();

		try(RandomAccessFile raf=new RandomAccessFile("TEST.class","r")){

			c.read(raf);

		}

		short ni=c._this_class().name_index;

		try(RandomAccessFile raf=new RandomAccessFile("TEST.class","rw")){

			c.write(raf);

		}

	}

	/*
	 Utf8 used by:
	 cp_info:
	  CONSTANT_Class_info: name_index;
	  CONSTANT_String_info: string_index;
	  CONSTANT_NameAndType_info: name_index, descriptor_index;
	  CONSTANT_MethodType_info: descriptor_index;
	 field_info: name_index, descriptor_index;
	 method_info: name_index, descriptor_index;
	 attribute_info: attribute_name_index,
	  InnerClasses_attribute:
	   class_structure: inner_name_index;
	  Signature_attribute: signature_index;
	  SourceFile_attribute: sourcefile_index;
	  LocalVariableTable:
	   local_variable_table_structure: name_index, descriptor_index;
	  LocalVariableTypeTable:
	   local_variable_type_table_structure: name_index, signature_index;
	  RuntimeVisibleAnnotations:
	   annotation:
	    type_index,
	    element_value_pairs_structure:
	     element_name_index,
	     element_value:
	      value_structure:
	       enum_cost_value_structure: type_name_index, const_name_index;
	       class_info_index;
	  RuntimeInvisibleAnnotations;
	  RuntimeVisibleParameterAnnotations;
	  RuntimeInvisibleParameterAnnotations;
	  AnnotationDefault;
	*/

}