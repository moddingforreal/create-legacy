package com.melonstudios.createlegacy.tileentity;

import com.melonstudios.createlegacy.tileentity.abstractions.AbstractTileEntityKineticRenderer;

public class TileEntityClutchRenderer extends AbstractTileEntityKineticRenderer<TileEntityClutch> {
    @Override
    public void render(TileEntityClutch te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);

        spinShaftModel(te, x, y, z, partialTicks, te.axis());
    }
}
