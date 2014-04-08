package com.github.ZXSkelobrine.TestPlugins;

import org.bukkit.entity.Player;

public class PlayerLog {

	private boolean done = false;
	private Player player = null;

	public PlayerLog(boolean done, Player player) {
		this.done = done;
		this.player = player;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public boolean isDone() {
		return done;
	}

	public Player getPlayer() {
		return player;
	}

	public void nullify() {
		player = null;
	}
}
