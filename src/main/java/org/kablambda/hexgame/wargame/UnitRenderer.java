package org.kablambda.hexgame.wargame;

import org.apache.commons.math3.geometry.euclidean.twod.Line;
import org.apache.commons.math3.geometry.euclidean.twod.Segment;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.util.FastMath;
import org.kablambda.hexgame.HexPixelCalculator;
import org.kablambda.hexgame.HexRenderer;
import org.kablambda.hexgame.UIParameters;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import static org.apache.commons.math3.geometry.euclidean.twod.Vector2D.angle;

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
            drawGoalArrow(g, unit);
        }
    }

    private void drawGoalArrow(Graphics2D g, Unit unit) {
        Line2D line = new Line2D.Double();
        Point2D start = calculator.center(unit.getLocation());
        Point2D end = calculator.center(unit.getGoal());
        Vector2D vector = new Vector2D(end.getX() - start.getX(), end.getY() - start.getY());
        Vector2D basis = vector.normalize();
        Vector2D missing = basis.scalarMultiply(uiParameters.getHexSideLength()/1.65);
        Vector2D arrowArm = basis.scalarMultiply(uiParameters.getHexSideLength()/2);
        Vector2D drawEndV = vector.subtract(0.8, missing)
        Point2D drawEnd = from(drawEndV);
        double angle = angle(vector, new Vector2D(1.0, 0));
        Line arm1 = new Line(drawEndV, angle - FastMath.PI/4, 0.01);
        Segment seg = new Segment()
        g.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.setColor(new Color(unit.getTeam().getColor().getColorSpace(), unit.getTeam().getColor().getComponents(new float[4]), 0.33f));
        line.setLine(from(missing), drawEnd);
        g.draw(line);
        line.setLine(drawEnd, );
    }

    private Point2D from(Vector2D vector) {
        return new Point2D.Double(vector.getX(), vector.getY());
    }
}
