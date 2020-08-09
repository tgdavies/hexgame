package org.kablambda.hexgame.wargame;

import org.kablambda.hexgame.HexPixelCalculator;
import org.kablambda.hexgame.HexRenderer;
import org.kablambda.hexgame.UIParameters;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.color.ColorSpace;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class UnitRenderer implements HexRenderer<Unit> {

    private final UIParameters uiParameters;
    private final NatoSymbolFactory natoSymbolFactory;
    private final HexPixelCalculator calculator;

    public UnitRenderer(UIParameters uiParameters, NatoSymbolFactory natoSymbolFactory, HexPixelCalculator calculator) {
        this.uiParameters = uiParameters;
        this.natoSymbolFactory = natoSymbolFactory;
        this.calculator = calculator;
    }

    @Override
    public void paint(Graphics2D g, Unit unit) {
        g.setColor(unit.getTeam().getColor());
        g.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(natoSymbolFactory.infantry(uiParameters.getHexSideLength()));
        Arc2D arc = new Arc2D.Double();
        arc.setArcByCenter(0, 0, uiParameters.getHexSideLength() * 0.7, unit.getFatigue() * 180, 180 - (unit.getFatigue() * 180), Arc2D.OPEN);
        g.setColor(unit.getTeam().getColor().darker());
        g.draw(arc);
        arc.setArcByCenter(0, 0, uiParameters.getHexSideLength() * 0.7, 0, -(180 - (unit.getSupply() * 180)), Arc2D.OPEN);
        g.draw(arc);
        if (unit.getGoal() != null) {
            Line2D line = new Line2D.Double();
            Point2D start = calculator.center(unit.getLocation());
            Point2D end = calculator.center(unit.getGoal());
            g.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.setColor(new Color(unit.getTeam().getColor().getColorSpace(), unit.getTeam().getColor().getComponents(new float[4]), 0.4f));
            line.setLine(0, 0, end.getX() - start.getX(), end.getY() - start.getY());
            g.draw(line);
        }
    }
}
