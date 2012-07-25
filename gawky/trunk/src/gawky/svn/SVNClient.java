package gawky.svn;

import gawky.lang.SaveDateFormat;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNRevisionProperty;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.ISVNEditor;
import org.tmatesoft.svn.core.io.ISVNFileRevisionHandler;
import org.tmatesoft.svn.core.io.SVNFileRevision;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.io.diff.SVNDeltaGenerator;
import org.tmatesoft.svn.core.io.diff.SVNDiffWindow;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class SVNClient
{
	static SaveDateFormat sdfSVN = new SaveDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	static SaveDateFormat SDFddmmyyyyHHmmss = new SaveDateFormat("dd.mm.yyyy HH:mm:ss");
	
	public static int REVISION_LATEST = -1;

	private String root;
	private String user;
	private String pass;

	public SVNClient(String user, String pass, String root)
	{
		this.user = user;
		this.pass = pass;
		this.root = root;
	}

	public static void main(String[] args) throws Exception
	{
		String root = "https://145.228.210.16/svn/bfs/Releaseplanung/ChangeRequests/TTSStore";
		String user = "harb05";
		String pass = "harb05";


		SVNClient svn = new SVNClient(user, pass, root);

		/*
		 * Datei an SVN senden
		 */

		String filename = "filename.pdf";
		
		InputStream data = new FileInputStream("c:/ingo_2011-09-22_Protokoll_Projekt_Kick-off_BFS1-1.pdf");
		svn.svnCommitFile(filename, data, "Txxxxx: file added 2");

		
		/**
		 * Datei aus SVN holen
		 */

		ByteArrayOutputStream baos = svn.svnReadFile(filename);

		baos.writeTo(System.out);

		System.out.println("\n----------------\n");

		/**
		 * Dateirevisionen aus SVN holen
		 */

		Map<Long, Date> revisions = svn.svnReadRevisions(filename);

		// absteigend sortieren
		List revisionids = Arrays.asList(revisions.keySet().toArray());
		Collections.reverse(revisionids);

		for(Object revision : revisionids)
		{
			String dateStamp = SDFddmmyyyyHHmmss.format(revisions.get(revision));
			System.out.println("Datum: " + dateStamp + " Revision: " + revision);
		}
	}

	public ByteArrayOutputStream svnReadFile(String filePath) throws Exception
	{
		return svnReadFile(filePath, REVISION_LATEST);
	}

	public ByteArrayOutputStream svnReadFile(String filePath, int revision) throws Exception
	{
		SVNRepository repository = openRepository();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		SVNNodeKind nodeKind = repository.checkPath(filePath, revision);

		if(nodeKind == SVNNodeKind.NONE || nodeKind == SVNNodeKind.DIR)
		{
			throw new Exception("No File or Directory");
		}

		repository.getFile(filePath, revision, null, baos);

		// SVNProperties fileProperties = new SVNProperties();
		// Iterator iterator = fileProperties.nameSet().iterator();
		//
		// // Displays file properties.
		// while(iterator.hasNext())
		// {
		// String propertyName = (String)iterator.next();
		// String propertyValue = fileProperties.getStringValue(propertyName);
		// System.out.println("File property: " + propertyName + "=" +
		// propertyValue);
		// }

		repository.closeSession();

		return baos;
	}

	public Map<Long, Date> svnReadRevisions(String filePath) throws Exception
	{
		final Map<Long, Date> revisions = new LinkedHashMap();

		SVNRepository repository = openRepository();

		long lastrevision = repository.getLatestRevision();

		repository.getFileRevisions(filePath, 0, lastrevision, false, new ISVNFileRevisionHandler()
		{
			public void applyTextDelta(String path, String baseChecksum) throws SVNException
			{
			}

			public void closeRevision(String token) throws SVNException
			{
			}

			public void openRevision(SVNFileRevision fileRevision) throws SVNException
			{
				String timestamp = fileRevision.getRevisionProperties().getStringValue(SVNRevisionProperty.DATE).substring(0, 19);

				try
				{
					revisions.put(new Long(fileRevision.getRevision()), sdfSVN.parse(timestamp));
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
			}

			public OutputStream textDeltaChunk(String path, SVNDiffWindow diffWindow) throws SVNException
			{
				return null;
			}

			public void textDeltaEnd(String path) throws SVNException
			{
			}
		});

		repository.closeSession();

		return revisions;
	}

	static
	{
		initSVNKit();
	}

	private static void initSVNKit()
	{
		// For using over http:// and https://
		DAVRepositoryFactory.setup();

		// For using over svn:// and svn+xxx://
		SVNRepositoryFactoryImpl.setup();

		// For using over file:///
		FSRepositoryFactory.setup();
	}

	private SVNRepository openRepository() throws Exception
	{
		SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(root));

		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(user, pass);
		repository.setAuthenticationManager(authManager);

		return repository;
	}

	public void svnCommitFile(String filePath, InputStream newData, String message) throws Exception
	{
		SVNRepository repository = openRepository();

		// ISVNWorkspaceMediator

		ISVNEditor editor = repository.getCommitEditor(message, null);

		// ADD FILE
		SVNCommitInfo commitInfo = svnAddFile(editor, filePath, newData);
		System.out.println("ADDED: " + commitInfo);

		// // CHANGE DELTA
		// editor = repository.getCommitEditor("Txxxxx: file changed", null);
		//
		// commitInfo = modifyFile(editor, filePath, oldData, newData));
		// System.out.println("The file was changed: " + commitInfo);

		repository.closeSession();
	}

	private SVNCommitInfo svnAddFile(ISVNEditor editor, String filePath, InputStream data) throws SVNException
	{
		editor.openRoot(-1);

		try
		{
			editor.addFile(filePath, null, -1);
		}
		catch(Exception e)
		{
			editor.openFile(filePath, -1);
		}

		editor.applyTextDelta(filePath, null);

		SVNDeltaGenerator deltaGenerator = new SVNDeltaGenerator();
		String checksum = deltaGenerator.sendDelta(filePath, data, editor, true);

		editor.closeFile(filePath, checksum);

		editor.closeDir();

		// commit
		return editor.closeEdit();
	}

	public SVNCommitInfo svnModifyFile(ISVNEditor editor, String filePath, InputStream newData) throws SVNException
	{
		return svnModifyFile(editor, filePath, null, newData);
	}

	public SVNCommitInfo svnModifyFile(ISVNEditor editor, String filePath, InputStream oldData, InputStream newData) throws SVNException
	{
		editor.openRoot(-1);

		editor.openFile(filePath, -1);

		editor.applyTextDelta(filePath, null);

		SVNDeltaGenerator deltaGenerator = new SVNDeltaGenerator();
		String checksum;

		if(oldData != null)
			checksum = deltaGenerator.sendDelta(filePath, oldData, 0, newData, editor, true);
		else
			checksum = deltaGenerator.sendDelta(filePath, newData, editor, true);

		editor.closeFile(filePath, checksum);

		editor.closeDir();

		// commit
		return editor.closeEdit();
	}

	public String getRoot()
	{
		return root;
	}

	public void setRoot(String root)
	{
		this.root = root;
	}

	public String getUser()
	{
		return user;
	}

	public void setUser(String user)
	{
		this.user = user;
	}

	public String getPass()
	{
		return pass;
	}

	public void setPass(String pass)
	{
		this.pass = pass;
	}
}
