/*******************************************************************************
 * Copyright (c) 2015 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Red Hat, Inc. - initial API and implementation
 *
 ******************************************************************************/
package testsnippets;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/*
 * NOTE: this snippet has a JFace/Platform UI dependency. If you would like to
 * make use of this snippet, please checkout the necessary sources and modify
 * the classpath manually.
 */

public class Bug478618_ControlDecorationsTest {
	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		Composite parent = new Composite(shell, SWT.NONE);
		GridLayoutFactory.fillDefaults().margins(10, 10).applyTo(parent);

		Group group = new Group(parent, SWT.NONE);
		group.setText("Test");
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(group);
		GridLayoutFactory.fillDefaults().numColumns(4).margins(6, 6).applyTo(group);

		Label nameLabel = new Label(group, SWT.NONE);
		nameLabel.setText("Key:");
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.CENTER).applyTo(nameLabel);

		Text nameText = new Text(group, SWT.BORDER);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).span(3, 1).applyTo(nameText);

		createDecoration(nameText);

		Label valueLabel = new Label(group, SWT.NONE);
		valueLabel.setText("Value:");
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.CENTER).applyTo(valueLabel);

		Text valueText = new Text(group, SWT.BORDER);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).span(3, 1).applyTo(valueText);

		createDecoration(valueText);

		shell.open();
		shell.setSize(400, 200);
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	private static void createDecoration(Control control) {
		ControlDecoration decoration = new ControlDecoration(control, SWT.LEFT | SWT.TOP);
		decoration.setDescriptionText("test message");
		FieldDecoration fieldDecoration = FieldDecorationRegistry.getDefault()
				.getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);
		decoration.setImage(fieldDecoration.getImage());
		decoration.show();
	}
}