package scan.genericprocess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.AC;
import listeners.BasicObservable;
import process.ProcessManager;
import process.TerminalProcess;
import process.io.ProcessStreamSiphon;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: Jan 5, 2016, 1:36:23 AM 
 */
public abstract class GenericProcess extends BasicObservable implements ProcessStreamSiphon {

	protected String name;

	protected String command;

	protected TerminalProcess process;

	protected List<Object> skimmed;

	public GenericProcess( String name, String command ) {
		this.name = name;
		this.command = command;
	}

	public abstract void handleLine( String line );

	public void execute() {
		execute( null );
	}

	public void execute( Object additional ) {
		if ( process == null ) {
			process = new TerminalProcess( name );
			ProcessManager.getInstance().registerSiphon( name, this );
		}
		skimmed = new ArrayList<>();
		executeCommand( additional );
		process.sendCommand( AC.ECHO_CMD + " " + AC.COMPLETE );
	}

	protected void executeCommand( Object additional ) {
		process.sendCommand( command );
	}

	@Override
	public void notifyProcessEnded( String name ) {
	}

	@Override
	public void notifyProcessStarted( String name ) {
	}

	@Override
	public void skimMessage( String name, String line ) {
		if ( name.equals( this.name ) ) {
			if ( line.equals( AC.COMPLETE ) ) {
				onComplete();
			} else {
				handleLine( line );
			}
		}
	}

	public void closeResources() {
		try {
			process.closeResources();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		process = null;
		ProcessManager.getInstance().removeSiphon( name, this );
	}

	public void onComplete() {
		try {
			notifyObservers( skimmed );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
}