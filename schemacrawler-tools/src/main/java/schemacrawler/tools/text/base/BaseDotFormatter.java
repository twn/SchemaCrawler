/*
 * SchemaCrawler
 * http://sourceforge.net/projects/schemacrawler
 * Copyright (c) 2000-2014, Sualeh Fatehi.
 * This library is free software; you can redistribute it and/or modify it under
 * the terms
 * of the GNU Lesser General Public License as published by the Free Software
 * Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 */

package schemacrawler.tools.text.base;


import static sf.util.Utility.NEWLINE;
import static sf.util.Utility.readResourceFully;

import java.awt.Color;
import java.util.logging.Logger;

import schemacrawler.schema.DatabaseInfo;
import schemacrawler.schema.JdbcDriverInfo;
import schemacrawler.schema.SchemaCrawlerInfo;
import schemacrawler.schemacrawler.SchemaCrawlerException;
import schemacrawler.tools.options.TextOutputFormat;
import schemacrawler.tools.options.OutputOptions;
import schemacrawler.tools.text.utility.Alignment;
import schemacrawler.tools.text.utility.TableCell;
import schemacrawler.tools.text.utility.TableRow;

/**
 * Text formatting of schema.
 *
 * @author Sualeh Fatehi
 */
public abstract class BaseDotFormatter<O extends BaseTextOptions>
  extends BaseFormatter<O>
{

  protected static final Logger LOGGER = Logger
    .getLogger(BaseDotFormatter.class.getName());

  protected BaseDotFormatter(final O options,
                             final boolean printVerboseDatabaseInfo,
                             final OutputOptions outputOptions)
    throws SchemaCrawlerException
  {
    super(options, printVerboseDatabaseInfo, outputOptions);
  }

  @Override
  public void begin()
  {
    final String text = readResourceFully("/dot.header.txt");
    out.println(text);
  }

  @Override
  public void end()
  {
    out.println("}");
    out.flush();
    //
    out.close();
  }

  @Override
  public void handle(final DatabaseInfo dbInfo)
  {
    if (options.isNoInfo() || dbInfo == null)
    {
      return;
    }

    final TableRow row = new TableRow(TextOutputFormat.html);
    row.add(newTableCell("Database:", Alignment.right, false, Color.white, 1));
    row.add(newTableCell(dbInfo.getProductName() + "  "
                             + dbInfo.getProductVersion(),
                         Alignment.left,
                         false,
                         Color.white,
                         1));

    out.append(row.toString()).append(NEWLINE);

  }

  @Override
  public void handle(final JdbcDriverInfo driverInfo)
  {
    if (options.isNoInfo() || driverInfo == null)
    {
      return;
    }

    final TableRow row = new TableRow(TextOutputFormat.html);
    row.add(newTableCell("Driver:", Alignment.right, false, Color.white, 1));
    row.add(newTableCell(driverInfo.getDriverName() + "  "
                             + driverInfo.getDriverVersion(),
                         Alignment.left,
                         false,
                         Color.white,
                         1));

    out.append(row.toString()).append(NEWLINE);
  }

  @Override
  public void handle(final SchemaCrawlerInfo schemaCrawlerInfo)
  {
    if (options.isNoInfo() || schemaCrawlerInfo == null)
    {
      return;
    }

    TableRow row = new TableRow(TextOutputFormat.html);
    row.add(newTableCell("Generated by:",
                         Alignment.right,
                         false,
                         Color.white,
                         1));
    row.add(newTableCell(schemaCrawlerInfo.getSchemaCrawlerProductName() + " "
                             + schemaCrawlerInfo.getSchemaCrawlerVersion(),
                         Alignment.left,
                         false,
                         Color.white,
                         1));

    out.append(row.toString()).append(NEWLINE);

    row = new TableRow(TextOutputFormat.html);
    row.add(newTableCell("Generated on:",
                         Alignment.right,
                         false,
                         Color.white,
                         1));
    row.add(newTableCell(schemaCrawlerInfo.getCrawlTimestamp(),
                         Alignment.left,
                         false,
                         Color.white,
                         1));

    out.append(row.toString()).append(NEWLINE);
  }

  @Override
  public void handleInfoEnd()
    throws SchemaCrawlerException
  {
    if (options.isNoInfo())
    {
      return;
    }

    out.append("      </table>    >").append(NEWLINE).append("    labeljust=r")
      .append(NEWLINE).append("    labelloc=b").append(NEWLINE).append("  ];")
      .append(NEWLINE).append(NEWLINE);
  }

  @Override
  public void handleInfoStart()
    throws SchemaCrawlerException
  {
    if (options.isNoInfo())
    {
      return;
    }
    out
      .append("  graph [fontcolor=\"#555555\", ")
      .append(NEWLINE)
      .append("    label=<")
      .append(NEWLINE)
      .append("<table color=\"#555555\" border=\"1\" cellborder=\"0\" cellspacing=\"0\">")
      .append(NEWLINE);
  }

  protected TableCell newTableCell(final String text,
                                   final Alignment align,
                                   final boolean emphasizeText,
                                   final Color bgColor,
                                   final int colspan)
  {
    return new TableCell(text,
                         0,
                         align,
                         emphasizeText,
                         "",
                         bgColor,
                         colspan,
                         TextOutputFormat.html);
  }

}
