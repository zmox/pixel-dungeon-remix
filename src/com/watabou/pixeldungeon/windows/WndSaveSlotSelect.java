package com.watabou.pixeldungeon.windows;

import java.io.IOException;

import com.nyrds.pixeldungeon.ml.R;
import com.watabou.noosa.Game;
import com.watabou.noosa.GameWithGoogleIap;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.PixelDungeon;
import com.watabou.pixeldungeon.SaveUtils;

public class WndSaveSlotSelect extends WndOptions implements GameWithGoogleIap.IntersitialPoint{
	
	private boolean saving;
	private String slot;
	
	
	WndSaveSlotSelect(boolean _saving) {
		super(Game.getVar(R.string.WndSaveSlotSelect_SelectSlot), "", "1", "2", "3", "4", "5", "6", "7", "8", "9");
		saving = _saving;
		
		if(!saving) {
			for (int i = 1; i<10; i++) {
				if(!SaveUtils.slotUsed(Integer.toString(i), Dungeon.heroClass)) {
					setEnabled(i-1, false);
				}
			}
		}
	}
	
	@Override
	protected void onSelect( int index ) {
		slot = Integer.toString(index+1);
		PixelDungeon.displayAd(this);
	}

	@Override
	public void returnToWork() {
		
		if(saving) {
			try {
				
				Dungeon.saveAll();
				SaveUtils.copySaveToSlot(slot, Dungeon.heroClass);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		SaveUtils.loadGame(slot, Dungeon.hero.heroClass);
	};
	
}