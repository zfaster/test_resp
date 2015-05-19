package com.zzy.base.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class TikaBasicUtil {
	public static FileModel fileToFileModel(File f) {
		Parser parser = new AutoDetectParser();
		InputStream is = null;
		try {
			Metadata metadata = new Metadata();
			metadata.set(Metadata.RESOURCE_NAME_KEY, f.getName());
			is = new FileInputStream(f);
			ContentHandler handler = new BodyContentHandler();
			ParseContext context = new ParseContext();
			context.set(Parser.class, parser);
			// 2、执行parser的parse()方法。
			parser.parse(is, handler, metadata, context);
			return new FileModel(metadata, handler);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (TikaException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String fileToTxt(File f) {
		// 1、创建一个parser
		Parser parser = new AutoDetectParser();
		InputStream is = null;
		try {
			Metadata metadata = new Metadata();
			metadata.set(Metadata.RESOURCE_NAME_KEY, f.getName());
			is = new FileInputStream(f);
			ContentHandler handler = new BodyContentHandler();
			ParseContext context = new ParseContext();
			context.set(Parser.class, parser);
			// 2、执行parser的parse()方法。
			parser.parse(is, handler, metadata, context);
			for (String name : metadata.names()) {
				System.out.println(name + ":" + metadata.get(name));
			}
			return handler.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (TikaException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static class FileModel {
		private Metadata metadata;
		private ContentHandler contentHandler;

		public FileModel() {
			super();
		}

		public FileModel(Metadata metadata, ContentHandler contentHandler) {
			super();
			this.metadata = metadata;
			this.contentHandler = contentHandler;
		}

		public Metadata getMetadata() {
			return metadata;
		}

		public void setMetadata(Metadata metadata) {
			this.metadata = metadata;
		}

		public ContentHandler getContentHandler() {
			return contentHandler;
		}

		public void setContentHandler(ContentHandler contentHandler) {
			this.contentHandler = contentHandler;
		}

	}
}