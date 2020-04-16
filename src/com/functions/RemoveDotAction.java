package com.functions;

import java.io.File;

public class RemoveDotAction extends Action {

	public RemoveDotAction(String path) {
		super("Remove dot");
		this.setPath(path);
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	public void startRemoveDot(String path, String items[]) {
		String dir = path + System.getProperty("file.separator");

		for (String name : items) {
			File file = new File(dir + name);

			if (file.isFile()) {
				int j = name.lastIndexOf('.');
				File newfile = new File(dir + name.substring(0, j).replace(".", " ").concat(name.substring(j)));
				file.renameTo(newfile);
			}
		}
	}

}
