package com.files;

public class FileFactory {
	public enum FileType {
		Directory, NornalFile, Text, Zip
	}

	public static DataNode createFileNode(FileType type, String path) {
		switch (type) {
		case Directory:
			return new DirectoryNode(path);
		case NornalFile:
			return new FileNode(path);
		case Text:
			return new TextNode(path);
		case Zip:
			return new ZipNode(path);
		}
		return null;
	}
}
