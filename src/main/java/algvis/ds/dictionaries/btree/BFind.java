/*******************************************************************************
 * Copyright (c) 2012-present Jakub Kováč, Jozef Brandýs, Katarína Kotrlová,
 * Pavol Lukča, Ladislav Pápay, Viktor Tomkovič, Tatiana Tóthová
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package algvis.ds.dictionaries.btree;

import java.util.HashMap;

import algvis.core.Algorithm;
import algvis.core.NodeColor;

public class BFind extends Algorithm {
    private final BTree T;
    private final int K;
    private final HashMap<String, Object> result = new HashMap<String, Object>();

    public BFind(BTree T, int x) {
        super(T.panel);
        this.T = T;
        K = x;
    }

    @Override
    public void runAlgorithm() {
        final BNode v = new BNode(T, K);
        v.setColor(NodeColor.FIND);
        addToScene(v);
        setHeader("search");
        result.put("node", null);
        if (T.getRoot() == null) {
            v.goToRoot();
            addStep("empty");
            pause();
            v.goDown();
            v.setColor(NodeColor.NOTFOUND);
            removeFromScene(v);
            addStep("notfound");
        } else {
            BNode w = T.getRoot();
            v.goTo(w);
            addStep("bstfindstart");
            pause();

            while (true) {
                if (w.isIn(v.keys[0])) {
                    addStep("found");
                    v.goDown();
                    v.setColor(NodeColor.FOUND);
                    result.put("node", w);
                    break;
                }
                if (w.isLeaf()) {
                    addStep("notfound");
                    v.setColor(NodeColor.NOTFOUND);
                    v.goDown();
                    break;
                }
                w = w.way(v.keys[0]);
                v.goTo(w);
                pause();
            }
        }
        removeFromScene(v);
    }

    @Override
    public HashMap<String, Object> getResult() {
        return result;
    }
}
