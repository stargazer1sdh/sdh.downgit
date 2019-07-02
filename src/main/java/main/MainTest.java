package main;


import java.io.IOException;
import java.util.List;

import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHCommit.File;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;
import org.kohsuke.github.PagedIterator;


public class MainTest {

	public static void main(String[] args) {
		GitHub github = null;
		try {
			github = GitHub.connectUsingPassword("stargazer1sdh", "");
			GHRepository rep = github.getRepository("gchq/Gaffer");
			PagedIterable<GHCommit> commits = rep.listCommits();
			PagedIterator<GHCommit> it = commits._iterator(20);
			while(it.hasNext()) {
				GHCommit co = it.next();
				System.out.println(co.getSHA1());
				List<File> fs = co.getFiles();
				for(File f:fs) {
					System.out.println(f.getPreviousFilename()+"\t"+f.getFileName());
					System.out.println(f.getRawUrl());
					System.out.println(f.getPatch());
				}
			}
			
//			Map<String, GHBranch> branches = rep.getBranches();
//			for(String br : branches.keySet()) {
//				System.out.println(br);
//			}
//			GHBranch master = branches.get("master");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
