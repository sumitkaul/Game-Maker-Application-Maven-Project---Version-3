package jBox2d.Actions;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Test;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import JBox2d.Actions.ActionChangeSpeed;

public class TestActionChangeSpeed {

	public static World world;
	public static LinkedHashMap<String,Body> bodies;
	private BodyDef bodyDef;
	private Body body;

	@Before
	public void setUp() throws Exception {
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position.set(0.0f, 4.0f);

		bodyDef.bullet=true;

		world =new World(new Vec2(0,9.8f),false);
		body = world.createBody(bodyDef);
	}
	

	@Test
	public void testDoAction()
	{
		ActionChangeSpeed action = new ActionChangeSpeed(8.00,13.0);
		action.doAction(body);
		assertNotNull(body);
		assertEquals((int)8.0 ,(int) body.getPosition().x);
		assertEquals((int)13.0 ,(int)body.getPosition().y);
	}
}
